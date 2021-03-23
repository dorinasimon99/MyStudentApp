package hu.bme.aut.mystudentapp.backend

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.amazonaws.auth.AWSCognitoIdentityProvider
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.tokens.CognitoUserToken
import com.amazonaws.services.cognitoidentity.model.CognitoIdentityProvider
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.aws.sigv4.CognitoUserPoolsAuthProvider
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.api.rest.RestResponse
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignInOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.AmplifyConfiguration
import com.amplifyframework.core.Consumer
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.UserCourse
import com.amplifyframework.datastore.generated.model.UserData
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent
import com.google.gson.Gson
import hu.bme.aut.mystudentapp.data.model.AddUserToGroupModel
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import okhttp3.FormBody
import org.json.JSONObject

object NetworkBackend {

    private val TAG = "NetworkBackend"

    private var userData = false

    fun initialize(applicationContext: Context): NetworkBackend {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)

            Log.i(TAG, "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", e)
        }

        Log.i(TAG, "registering hub event")

        // listen to auth event
        Amplify.Hub.subscribe(HubChannel.AUTH) { hubEvent: HubEvent<*> ->

            when (hubEvent.name) {
                InitializationStatus.SUCCEEDED.toString() -> {
                    Log.i(TAG, "Amplify successfully initialized")
                }
                InitializationStatus.FAILED.toString() -> {
                    Log.i(TAG, "Amplify initialization failed")
                }
                else -> {
                    when (AuthChannelEventName.valueOf(hubEvent.name)) {
                        AuthChannelEventName.SIGNED_IN -> {
                            updateUserData(true)
                            Log.i(TAG, "HUB : SIGNED_IN")
                        }
                        AuthChannelEventName.SIGNED_OUT -> {
                            updateUserData(false)
                            Log.i(TAG, "HUB : SIGNED_OUT")
                        }
                        else -> Log.i(TAG, """HUB EVENT:${hubEvent.name}""")
                    }
                }
            }
        }

        Log.i(TAG, "retrieving session status")

        // is user already authenticated (from a previous execution) ?
        Amplify.Auth.fetchAuthSession(
            { result ->
                Log.i(TAG, result.toString())
                val cognitoAuthSession = result as AWSCognitoAuthSession
                // update UI
                this.updateUserData(cognitoAuthSession.isSignedIn)
                when (cognitoAuthSession.identityId.type) {
                    AuthSessionResult.Type.SUCCESS -> Log.i(
                        TAG,
                        "IdentityId: " + cognitoAuthSession.identityId.value
                    )
                    AuthSessionResult.Type.FAILURE -> Log.i(
                        TAG,
                        "IdentityId not present because: " + cognitoAuthSession.identityId.error.toString()
                    )
                }
            },
            { error -> Log.i(TAG, error.toString()) }
        )
        return this
    }

    fun updateUserData(withSignedInStatus: Boolean) {
        UserDataBackend.setSignedIn(withSignedInStatus)
        userData = withSignedInStatus

        //UserDataBackend.getUserData()
    }

    fun userData() = userData

    fun signOut() {
        Log.i(TAG, "Initiate Signout Sequence")

        Amplify.Auth.signOut(
            { Log.i(TAG, "Signed out!") },
            { error -> Log.e(TAG, error.toString()) }
        )
    }

    fun signIn(callingActivity: Activity) {
        Log.i(TAG, "Initiate Signin Sequence")

        Amplify.Auth.signInWithWebUI(
            callingActivity,
            { result: AuthSignInResult -> Log.i(TAG, result.toString()) },
            { error: AuthException -> Log.e(TAG, error.toString()) }
        )
    }

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

    fun createCourse(c: Course) {
        Log.i(TAG, "Creating course")

        Amplify.API.mutate(
            ModelMutation.create(c.data),
            { response ->
                Log.i(TAG, "Course created")
                if(response.hasErrors()){
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created course with name: " + response.data.name)
                }
            },
            { error -> Log.e(TAG, "Course create failed", error)}
        )
    }

    private fun getCourseForUser(courseId: String) : Boolean {
        var isCorrectCourse = false
        Amplify.API.query(
            ModelQuery.get(UserCourse::class.java, courseId),
            { response ->
                Log.i(TAG, "get courses for user")
                if (response.data != null && response.data.course.id == courseId && response.data.user.id == UserDataBackend.currentUser.id) {
                    isCorrectCourse = true
                }
            },
            { error -> Log.e("UserDataBackend", "Get course for user failed: ", error) }
        )
        return isCorrectCourse
    }

    fun getCourses() {
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i("UserDataBackend", "Courses queried")
                for (courseData in response.data) {
                    if (getCourseForUser(courseData.id)) {
                        UserDataBackend.addCourse(Course.from(courseData))
                    }
                }
            },
            { error -> Log.e("UserDataBackend", "Courses query failure:", error) }
        )
    }

    fun getAllCourses(){
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i(TAG, "Queried all courses")
                for (courseData in response.data) {
                    UserDataBackend.addCourse(Course.from(courseData))
                }
            },
            { error -> Log.e(TAG, "All courses query failure:", error) }
        )
    }

}
package hu.bme.aut.mystudentapp.backend

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import hu.bme.aut.mystudentapp.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.FormBody
import org.json.JSONObject
import java.util.*

object NetworkBackend {

    private val TAG = "NetworkBackend"

    private var userData : Boolean? = null

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

    private fun updateUserData(withSignedInStatus: Boolean) {
        UserDataBackend.setSignedIn(withSignedInStatus)
        userData = withSignedInStatus
    }

    fun userData() = userData!!

    fun signOut() {
        Log.i(TAG, "Initiate Signout Sequence")

        Amplify.Auth.signOut(
            {
                Log.i(TAG, "Signed out!")
            },
            { error -> Log.e(TAG, error.toString()) }
        )

    }

    fun signIn(username: String, password: String) {
        Log.i(TAG, "Initiate Signin Sequence")

        Amplify.Auth.signIn(
            username,
            password,
            {result: AuthSignInResult ->
                Log.i(TAG, result.toString())},
            {error: AuthException -> Log.e(TAG, error.toString())}
        )
    }

    fun signUp(username: String, password: String, email: String){
        Log.i(TAG, "Initiate SignUp Sequence")

        Amplify.Auth.signUp(
            username,
            password,
            AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
            {result: AuthSignUpResult -> Log.i(TAG, result.toString())},
            {error: AuthException -> Log.i(TAG, error.toString())}
        )
    }

    fun confirmSignUp(username: String, confirmCode: String, password: String){
        Log.i(TAG, "Initiate Confirm SignUp Sequence")

        Amplify.Auth.confirmSignUp(
            username,
            confirmCode,
            {result: AuthSignUpResult -> Log.i(TAG, result.toString())},
            {error: AuthException -> Log.i(TAG, error.toString())}
        )

        signIn(username, password)
    }

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }
}
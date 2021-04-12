package hu.bme.aut.mystudentapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import javax.inject.Inject

class SignInScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
){
    suspend fun signIn(callingActivity: Activity) {
        userInteractor.signIn(callingActivity)
    }

    suspend fun setSignedIn(newValue: Boolean) {
        userInteractor.setSignedIn(newValue)
    }

    suspend fun getUserData(): User?{
        return userInteractor.getUserData()
    }

    suspend fun setUserData(user: User){
        userInteractor.setUserData(user)
    }

    /*suspend fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        userInteractor.handleWebUISignInResponse(requestCode, resultCode, data)
    }*/

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SignInScreenPresenter", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

}
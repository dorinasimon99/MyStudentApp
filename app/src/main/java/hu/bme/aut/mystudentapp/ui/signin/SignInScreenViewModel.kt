package hu.bme.aut.mystudentapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import java.lang.Exception
import javax.inject.Inject

class SignInScreenViewModel @Inject constructor(
    private val signInScreenPresenter: SignInScreenPresenter
) : RainbowCakeViewModel<SignInScreenViewState>(SignInInitial) {

    fun signIn(activity: Activity) = execute {
        viewState = SignInInitial
        viewState = try {
            signInScreenPresenter.signIn(activity)
            signInScreenPresenter.setSignedIn(true)
            val user = signInScreenPresenter.getUserData()
            SignInLoading(user)
        } catch (e: Exception){
            SignInError
        }
    }
    /*fun handleWebUI(requestCode: Int, resultCode: Int, data: Intent?) = execute {
        signInScreenPresenter.handleWebUISignInResponse(requestCode, resultCode, data)
    }*/

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SignInScreenViewModel", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

}
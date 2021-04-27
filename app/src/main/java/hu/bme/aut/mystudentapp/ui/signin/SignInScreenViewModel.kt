package hu.bme.aut.mystudentapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.main.MainScreenError
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import javax.inject.Inject

class SignInScreenViewModel @Inject constructor(
    private val signInScreenPresenter: SignInScreenPresenter
) : RainbowCakeViewModel<SignInScreenViewState>(SignInInitial) {

    fun signIn(username: String, password: String) = execute {
        try {
            signInScreenPresenter.signIn(username, password)
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
        viewState = SignInInitial
        /*try {
            signInScreenPresenter.setSignedIn(true)
        } catch (e: Exception){
            viewState = SignInError(Exception("Setting sign in value failed"))
        }*/
        /*try {
            signInScreenPresenter.isSignedIn()
        } catch (e: Exception){
            return@execute
        }*/
        viewState = SignedIn
        try {
            signInScreenPresenter.getUserData(username)
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
    }

    fun signUp(username: String, password: String, email: String) = execute {
        try {
            signInScreenPresenter.signUp(username, password, email)
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
        viewState = SignUp
    }

    fun confirmSignUp(username: String, confirmCode: String, password: String) = execute {
        try {
            signInScreenPresenter.confirmSignUp(username, confirmCode, password)
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
        viewState = ConfirmedSignUp
    }

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SignInScreenViewModel", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

}
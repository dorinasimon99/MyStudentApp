package hu.bme.aut.mystudentapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
){
    suspend fun signIn(username: String, password: String) {
        userInteractor.signIn(username, password)
    }

    suspend fun setSignedIn(newValue: Boolean) = withIOContext {
        userInteractor.setSignedIn(newValue)
    }

    suspend fun getUserData(username: String) = withIOContext {
        userInteractor.getUserData(username)
    }

    suspend fun setUserData(user: User) = withIOContext {
        userInteractor.setUserData(user)
    }

    suspend fun getLocalUserData() : LocalUserData?  = withIOContext  {
        userInteractor.getLocalUser()
    }

    suspend fun isSignedIn() : Boolean = withIOContext {
        userInteractor.isSignedIn()
    }

    suspend fun signUp(username: String, password: String, email: String){
        userInteractor.signUp(username, password, email)
    }

    suspend fun confirmSignUp(username: String, confirmCode: String, password: String){
        userInteractor.confirmSignUp(username, confirmCode, password)
    }

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SignInScreenPresenter", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

}
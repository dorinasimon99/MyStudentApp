package hu.bme.aut.mystudentapp.ui.signin

import android.app.Activity
import android.content.Intent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
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

    fun handleWebUI(requestCode: Int, resultCode: Int, data: Intent?) = execute {
        signInScreenPresenter.handleWebUISignInResponse(requestCode, resultCode, data)
    }
}
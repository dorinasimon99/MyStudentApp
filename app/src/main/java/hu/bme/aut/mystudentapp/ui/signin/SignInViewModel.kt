package hu.bme.aut.mystudentapp.ui.signin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import java.lang.Exception
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<SignInScreenViewState>(SignInInitial) {

    fun signIn(username: String, password: String) = execute {
        viewState = SignInInitial
        try {
            val message = userInteractor.signIn(username, password)
            if(message == ""){
                viewState = SignedIn
                viewState = SignInUserData(userInteractor.getUserData(userInteractor.getLocalUser()?.username))
            }
            else {
                viewState = SignInError(Exception(message))
            }
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
    }

    fun signUp(username: String, password: String, email: String) = execute {
        try {
            val message = userInteractor.signUp(username, password, email)
            if(message != null){
                viewState = SignInError(Exception(message))
            }
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
    }

    fun confirmSignUp(username: String, confirmCode: String, password: String) = execute {
        try {
            val message = userInteractor.confirmSignUp(username, confirmCode, password)
            if(message == ""){
                viewState = ConfirmedSignUp
            }
            else viewState = SignInError(Exception(message))
        } catch (e: Exception) {
            viewState = SignInError(e)
        }
    }
}
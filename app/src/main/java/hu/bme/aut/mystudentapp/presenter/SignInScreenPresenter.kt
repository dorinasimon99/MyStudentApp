package hu.bme.aut.mystudentapp.presenter

import android.app.Activity
import android.content.Intent
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

    suspend fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        userInteractor.handleWebUISignInResponse(requestCode, resultCode, data)
    }

}
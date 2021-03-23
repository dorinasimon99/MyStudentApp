package hu.bme.aut.mystudentapp.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import javax.inject.Inject

class MainScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getUserData() : User? {
        return userInteractor.getUserData()
    }

    var isSignedIn: LiveData<Boolean> = userInteractor.isSignedIn

    suspend fun setSignedIn(newValue: Boolean) {
        userInteractor.setSignedIn(newValue)
    }

    var hasUserData: LiveData<User> = userInteractor.hasUserData


    suspend fun setUserData(user: User){
        userInteractor.setUserData(user)
    }

    suspend fun initialize(applicationContext: Context) {
        userInteractor.initialize(applicationContext)
    }

    suspend fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        userInteractor.handleWebUISignInResponse(requestCode, resultCode, data)
    }

    suspend fun signOut() {
        userInteractor.signOut()
    }

    suspend fun signIn(callingActivity: Activity) {
        userInteractor.signIn(callingActivity)
    }
}
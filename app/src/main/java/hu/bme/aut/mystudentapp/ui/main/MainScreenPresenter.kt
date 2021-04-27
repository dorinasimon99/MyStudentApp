package hu.bme.aut.mystudentapp.ui.main

import android.app.Activity
import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getLocalUser() : LocalUserData?  {
        return userInteractor.getLocalUser()
    }

    suspend fun isSignedIn() : Boolean {
        return userInteractor.isSignedIn()
    }

    suspend fun getUserData(username: String?) = withIOContext{
        userInteractor.getUserData(username)
    }
}
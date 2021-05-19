package hu.bme.aut.mystudentapp.ui.main

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import co.zsmb.rainbowcake.base.RainbowCakeActivity
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<MainScreenViewState>(MainScreenInitial) {

    fun getLocalUser(lifecycleOwner: LifecycleOwner) = execute {
        viewState = MainScreenInitial
        try {
            userInteractor.isSignedIn().observe(lifecycleOwner, {isSignedIn ->
                if(isSignedIn){
                    signedIn()
                } else viewState = MainScreenSignedOut
            })

        } catch (e: Exception) {
            viewState = MainScreenError(e)
        }
        /*try {
        } catch (e: Exception){
            viewState = MainScreenError(e)
        }*/
    }

    fun signedIn() = execute {
        val localUser = userInteractor.getLocalUser()
        viewState = MainScreenLocalUser(localUser)
        viewState = MainScreenUserData(userInteractor.getUserData(localUser?.username))
    }
}
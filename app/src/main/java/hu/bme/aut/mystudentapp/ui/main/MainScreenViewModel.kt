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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import java.lang.Exception
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val mainScreenPresenter: MainScreenPresenter
) : RainbowCakeViewModel<MainScreenViewState>(MainScreenInitial) {

    fun getLocalUser(lifecycleOwner: LifecycleOwner) = execute {
        viewState = MainScreenInitial
        var localUser: LocalUserData? = null
        try {
            localUser = mainScreenPresenter.getLocalUser()
        } catch (e: Exception) {
            viewState = MainScreenError(e)
        }
        viewState = MainScreenLocalUser(localUser)
        try {
            mainScreenPresenter.getUserData(localUser?.username)
        } catch (e: Exception){
            viewState = MainScreenError(e)
        }


    }

}
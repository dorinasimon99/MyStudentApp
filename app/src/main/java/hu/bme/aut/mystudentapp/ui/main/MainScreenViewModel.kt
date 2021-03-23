package hu.bme.aut.mystudentapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.ui.courses.Initial
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val mainScreenPresenter: MainScreenPresenter
): RainbowCakeViewModel<MainScreenViewState>(hu.bme.aut.mystudentapp.ui.main.Initial) {

    private val TAG = "MainScreenViewModel"

    fun loadUserData(activity : AppCompatActivity) = execute {
        viewState = Loading
        viewState = try {

            var userData = mainScreenPresenter.hasUserData.value
            var isSignedIn = false
            mainScreenPresenter.isSignedIn.observe(activity, Observer<Boolean> { isSignedUp ->
                if (isSignedUp) {
                    if (userData != null) {
                        isSignedIn = true
                    }
                }
                else isSignedIn = false
            })
            if(isSignedIn){
                DataReady(mainScreenPresenter.getUserData())
            }
            else SignIn
        } catch (e: Exception){
            DataError
        }
    }
}
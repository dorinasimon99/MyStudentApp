package hu.bme.aut.mystudentapp.ui.selectrole

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import java.lang.Exception
import javax.inject.Inject

class SelectRoleViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<SelectRoleScreenViewState>(SelectRoleInitial) {

    fun selectRole(user : User) = execute {
        viewState = try {
            userInteractor.setUserData(user)
            SelectRole
        }catch (e : Exception){
            Log.e("SelectRoleScreenViewModel", "error", e)
            SelectRoleError
        }
    }
}
package hu.bme.aut.mystudentapp.ui.selectrole

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.User
import java.lang.Exception
import javax.inject.Inject

class SelectRoleScreenViewModel @Inject constructor(
    private val selectRoleScreenPresenter: SelectRoleScreenPresenter
) : RainbowCakeViewModel<SelectRoleScreenViewState>(SelectRoleInitial) {

    fun selectRole(user : User) = execute {
        viewState = try {
            selectRoleScreenPresenter.setUserData(user)
            SelectRole
        }catch (e : Exception){
            SelectRoleError
        }
    }
}
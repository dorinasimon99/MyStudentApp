package hu.bme.aut.mystudentapp.viewmodel

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.presenter.SelectRoleScreenPresenter
import hu.bme.aut.mystudentapp.viewstate.SelectRole
import hu.bme.aut.mystudentapp.viewstate.SelectRoleError
import hu.bme.aut.mystudentapp.viewstate.SelectRoleInitial
import hu.bme.aut.mystudentapp.viewstate.SelectRoleScreenViewState
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
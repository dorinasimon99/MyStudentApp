package hu.bme.aut.mystudentapp.viewstate

sealed class SelectRoleScreenViewState

object SelectRoleInitial : SelectRoleScreenViewState()

object SelectRole : SelectRoleScreenViewState()

object SelectRoleError : SelectRoleScreenViewState()
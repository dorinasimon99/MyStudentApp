package hu.bme.aut.mystudentapp.ui.selectrole

sealed class SelectRoleScreenViewState

object SelectRoleInitial : SelectRoleScreenViewState()

object SelectRole : SelectRoleScreenViewState()

object SelectRoleError : SelectRoleScreenViewState()
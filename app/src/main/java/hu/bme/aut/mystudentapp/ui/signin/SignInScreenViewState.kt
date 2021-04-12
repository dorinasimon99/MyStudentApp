package hu.bme.aut.mystudentapp.ui.signin

import hu.bme.aut.mystudentapp.data.model.User

sealed class SignInScreenViewState

object SignInInitial : SignInScreenViewState()

data class SignInLoading(val user: User?) : SignInScreenViewState()

object SignInError : SignInScreenViewState()
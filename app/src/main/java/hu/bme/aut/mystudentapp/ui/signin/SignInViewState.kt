package hu.bme.aut.mystudentapp.ui.signin

import hu.bme.aut.mystudentapp.data.model.User
import java.lang.Exception

sealed class SignInScreenViewState

object SignInInitial : SignInScreenViewState()

object SignedIn : SignInScreenViewState()

data class SignInUserData(val user: User?) : SignInScreenViewState()

object ConfirmedSignUp : SignInScreenViewState()

data class SignInError(val e: Exception) : SignInScreenViewState()
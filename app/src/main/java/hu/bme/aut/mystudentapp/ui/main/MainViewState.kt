package hu.bme.aut.mystudentapp.ui.main

import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import java.lang.Exception

sealed class MainScreenViewState

object MainScreenInitial : MainScreenViewState()

object MainScreenSignedOut : MainScreenViewState()

data class MainScreenLocalUser(val user: LocalUserData?) : MainScreenViewState()

data class MainScreenUserData(val user: User?) : MainScreenViewState()

data class MainScreenError(val error: Exception) : MainScreenViewState()
package hu.bme.aut.mystudentapp.ui.main

import hu.bme.aut.mystudentapp.data.model.User

sealed class MainScreenViewState

object Initial : MainScreenViewState()

object Loading : MainScreenViewState()

object SignIn : MainScreenViewState()

data class DataReady(val user: User?) : MainScreenViewState()

object DataError : MainScreenViewState()
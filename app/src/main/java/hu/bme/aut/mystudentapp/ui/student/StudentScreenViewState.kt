package hu.bme.aut.mystudentapp.ui.student

import hu.bme.aut.mystudentapp.data.model.User

sealed class StudentScreenViewState

object StudentBegin : StudentScreenViewState()

data class StudentInitial(val user: User?) : StudentScreenViewState()

object StudentError : StudentScreenViewState()
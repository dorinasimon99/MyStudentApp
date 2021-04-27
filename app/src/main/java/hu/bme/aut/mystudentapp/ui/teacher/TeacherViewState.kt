package hu.bme.aut.mystudentapp.ui.teacher

import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.student.StudentScreenViewState

sealed class TeacherViewState

object TeacherBegin : TeacherViewState()

data class TeacherInitial(val user: User?) : TeacherViewState()

object TeacherError : TeacherViewState()
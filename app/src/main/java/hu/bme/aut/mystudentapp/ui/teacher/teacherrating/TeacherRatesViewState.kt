package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

sealed class TeacherRatesViewState

object TeacherRatesInitial : TeacherRatesViewState()

object TeacherRatesError : TeacherRatesViewState()
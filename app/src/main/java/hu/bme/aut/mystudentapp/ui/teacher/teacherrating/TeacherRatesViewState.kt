package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.StudentComment
import hu.bme.aut.mystudentapp.data.model.TeacherRate

sealed class TeacherRatesViewState

object TeacherRatesInitial : TeacherRatesViewState()

data class TeacherCoursesLoaded(val courses: List<String>) : TeacherRatesViewState()

data class TeacherRatesAndCommentsLoaded(val rates: List<TeacherRate>?, val comments: List<StudentComment>?) : TeacherRatesViewState()

data class TeacherCommentsUpdated(val comments: List<StudentComment>?) : TeacherRatesViewState()

data class TeacherRatesUpdated(val rates: List<TeacherRate>?) : TeacherRatesViewState()

object TeacherRatesError : TeacherRatesViewState()
package hu.bme.aut.mystudentapp.ui.courses

sealed class AddCourseViewState

object AddCourse : AddCourseViewState()

object CourseAdded : AddCourseViewState()

object AddCourseError : AddCourseViewState()
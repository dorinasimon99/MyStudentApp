package hu.bme.aut.mystudentapp.ui.courses.addcourse

sealed class AddCourseViewState

object AddCourse : AddCourseViewState()

object CourseAdded : AddCourseViewState()

object AddCourseError : AddCourseViewState()
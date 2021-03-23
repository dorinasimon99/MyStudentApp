package hu.bme.aut.mystudentapp.ui.courses


sealed class CoursesViewState

object Initial : CoursesViewState()

object CoursesLoading : CoursesViewState()

object CourseLoadingError : CoursesViewState()
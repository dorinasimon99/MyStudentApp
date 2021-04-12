package hu.bme.aut.mystudentapp.ui.courses.searchcourses

sealed class SearchCoursesViewState

object SearchCoursesInitial : SearchCoursesViewState()

object ListSearchCourses : SearchCoursesViewState()

object SearchCoursesError : SearchCoursesViewState()
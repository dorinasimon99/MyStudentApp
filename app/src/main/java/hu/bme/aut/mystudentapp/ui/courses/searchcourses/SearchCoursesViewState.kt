package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import java.lang.Exception

sealed class SearchCoursesViewState

object SearchCoursesInitial : SearchCoursesViewState()

object ListSearchCourses : SearchCoursesViewState()

object ListCourseAdded : SearchCoursesViewState()

data class SearchCoursesError(val e: Exception) : SearchCoursesViewState()
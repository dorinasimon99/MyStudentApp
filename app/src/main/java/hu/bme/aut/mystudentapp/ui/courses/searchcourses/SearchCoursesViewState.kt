package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import hu.bme.aut.mystudentapp.data.model.Course
import java.lang.Exception

sealed class SearchCoursesViewState

object SearchCoursesInitial : SearchCoursesViewState()

data class ListSearchCourses(val courses: List<Course>?) : SearchCoursesViewState()

object ListCourseAdded : SearchCoursesViewState()

data class SearchCoursesError(val e: Exception) : SearchCoursesViewState()
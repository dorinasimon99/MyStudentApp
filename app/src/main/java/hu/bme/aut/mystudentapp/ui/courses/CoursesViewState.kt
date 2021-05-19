package hu.bme.aut.mystudentapp.ui.courses

import hu.bme.aut.mystudentapp.data.model.Course


sealed class CoursesViewState

object Initial : CoursesViewState()

data class CoursesLoading(val courses: List<Course>?) : CoursesViewState()

object CourseLoadingError : CoursesViewState()
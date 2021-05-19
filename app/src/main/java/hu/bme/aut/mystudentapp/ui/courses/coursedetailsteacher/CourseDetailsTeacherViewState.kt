package hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher

sealed class CourseDetailsTeacherViewState

object CourseDetailsTeacherInitial : CourseDetailsTeacherViewState()

data class CourseDetailsTeacherLessons(val lesson: List<Lesson>?) : CourseDetailsTeacherViewState()

data class CourseDetailsTeacherError(val e: Exception) : CourseDetailsTeacherViewState()
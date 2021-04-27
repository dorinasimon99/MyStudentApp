package hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher

sealed class CourseDetailsTeacherViewState

object CourseDetailsTeacherInitial : CourseDetailsTeacherViewState()

object CourseDetailsTeacherError : CourseDetailsTeacherViewState()
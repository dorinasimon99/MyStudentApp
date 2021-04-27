package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

sealed class CourseDetailsViewState

object CourseDetailsInitial : CourseDetailsViewState()

object CourseDetailsTeachers : CourseDetailsViewState()

object CourseDetailsError : CourseDetailsViewState()
package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import hu.bme.aut.mystudentapp.data.model.Todo

sealed class CourseDetailsViewState

object CourseDetailsInitial : CourseDetailsViewState()

data class CourseDetailsLoading(val todos: List<Todo>?, val teachers: List<Teacher>?) : CourseDetailsViewState()

data class CourseDetailsModifyTodos(val todos: List<Todo>?) : CourseDetailsViewState()

object CourseDetailsError : CourseDetailsViewState()
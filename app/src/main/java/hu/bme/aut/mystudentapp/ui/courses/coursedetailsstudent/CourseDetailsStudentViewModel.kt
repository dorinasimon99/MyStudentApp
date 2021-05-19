package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

class CourseDetailsStudentViewModel @Inject constructor(
    private val coursesInteractor: CoursesInteractor,
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<CourseDetailsViewState>(CourseDetailsInitial) {

    fun load(courseCode: String, courseName: String) = execute {
        viewState = CourseDetailsInitial
        viewState = try {
            CourseDetailsLoading(
                coursesInteractor.loadCourseDetails(courseCode),
                coursesInteractor.loadTeachers(courseName)
            )
        } catch (e: Exception) {
            CourseDetailsError
        }
    }

    fun deleteTodo(todo: Int) = execute {
        delay(1000)
        viewState = CourseDetailsModifyTodos(coursesInteractor.deleteTodo(todo))
    }

    fun addTodo(todo: Todo) = execute {
        viewState = CourseDetailsModifyTodos(coursesInteractor.addTodo(todo))
    }
}
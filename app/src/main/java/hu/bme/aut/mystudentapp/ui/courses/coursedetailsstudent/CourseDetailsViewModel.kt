package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.ui.courses.CoursesPresenter
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

class CourseDetailsViewModel @Inject constructor(
    private val courseDetailsPresenter: CoursesPresenter
) : RainbowCakeViewModel<CourseDetailsViewState>(CourseDetailsInitial) {

    fun load() = execute {
        viewState = try {
            courseDetailsPresenter.loadCourseDetails()
            CourseDetailsInitial
        } catch (e: Exception) {
            CourseDetailsError
        }
    }

    fun loadTeachers(course: String) = execute {
        courseDetailsPresenter.loadTeachers(course)
        CourseDetailsTeachers
    }

    fun deleteTodo(todo: Int) = execute {
        delay(1000)
        courseDetailsPresenter.deleteTodo(todo)
    }
}
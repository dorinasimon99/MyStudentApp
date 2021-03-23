package hu.bme.aut.mystudentapp.ui.courses

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.Course
import java.lang.Exception
import javax.inject.Inject

class AddCourseViewModel @Inject constructor(
    private val coursesPresenter: CoursesPresenter
) : RainbowCakeViewModel<AddCourseViewState>(AddCourse) {

    fun addCourse(course: Course) = execute {
        viewState = AddCourse
        viewState = try {
            coursesPresenter.addCourse(course)
            CourseAdded
        } catch (e: Exception) {
            AddCourseError
        }
    }
}
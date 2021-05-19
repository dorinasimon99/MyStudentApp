package hu.bme.aut.mystudentapp.ui.courses.addcourse

import android.content.Context
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import hu.bme.aut.mystudentapp.ui.courses.*
import java.lang.Exception
import javax.inject.Inject

class AddCourseViewModel @Inject constructor(
    private val coursesInteractor: CoursesInteractor,
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<AddCourseViewState>(AddCourse) {

    fun addCourse(course: Course) = execute {
        viewState = AddCourse
        viewState = try {
            coursesInteractor.addCourse(course)
            CourseAdded
        } catch (e: Exception) {
            AddCourseError
        }
    }
}
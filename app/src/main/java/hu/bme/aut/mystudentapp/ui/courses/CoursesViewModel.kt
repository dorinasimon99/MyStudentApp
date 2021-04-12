package hu.bme.aut.mystudentapp.ui.courses

import android.content.Context
import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import java.lang.Exception
import javax.inject.Inject

class CoursesViewModel @Inject constructor(
    private val coursesPresenter: CoursesPresenter
) : RainbowCakeViewModel<CoursesViewState>(Initial) {
    fun loadCourses() = execute {
        viewState = Initial
        viewState = try {
            coursesPresenter.loadCourses()
            CoursesLoading
        } catch (e: Exception){
            CourseLoadingError
        }
    }
}
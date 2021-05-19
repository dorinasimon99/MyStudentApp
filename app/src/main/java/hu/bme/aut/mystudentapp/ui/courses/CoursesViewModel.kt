package hu.bme.aut.mystudentapp.ui.courses

import android.content.Context
import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import java.lang.Exception
import javax.inject.Inject

class CoursesViewModel @Inject constructor(
    private val coursesInteractor: CoursesInteractor,
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<CoursesViewState>(Initial) {
    fun loadCourses() = execute {
        viewState = Initial
        viewState = try {
            CoursesLoading(coursesInteractor.loadCourses())
        } catch (e: Exception){
            CourseLoadingError
        }
    }

    fun saveToLocal(courses: List<Course>) = execute {
        coursesInteractor.saveToLocal(courses)
    }
}
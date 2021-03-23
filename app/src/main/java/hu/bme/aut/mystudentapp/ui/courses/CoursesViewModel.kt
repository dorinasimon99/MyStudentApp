package hu.bme.aut.mystudentapp.ui.courses

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
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
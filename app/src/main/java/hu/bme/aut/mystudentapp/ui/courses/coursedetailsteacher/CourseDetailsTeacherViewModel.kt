package hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import java.lang.Exception
import javax.inject.Inject

class CourseDetailsTeacherViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<CourseDetailsTeacherViewState>(CourseDetailsTeacherInitial) {
    fun load(teachername: String, courseName: String) = execute {
        viewState = CourseDetailsTeacherInitial
        viewState = try {
            CourseDetailsTeacherLessons(userInteractor.getLessons(teachername, courseName))
        } catch (e: Exception){
            CourseDetailsTeacherError(e)
        }
    }
}
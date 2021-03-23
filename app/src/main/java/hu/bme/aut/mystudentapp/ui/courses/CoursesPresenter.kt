package hu.bme.aut.mystudentapp.ui.courses

import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import javax.inject.Inject

class CoursesPresenter @Inject constructor(
    private val coursesInteractor: CoursesInteractor
){
    suspend fun addCourse(course: Course){
        coursesInteractor.addCourse(course)
    }

    suspend fun loadCourses(){
        coursesInteractor.loadCourses()
    }
}
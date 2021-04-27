package hu.bme.aut.mystudentapp.ui.courses

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import javax.inject.Inject

class CoursesPresenter @Inject constructor(
    private val coursesInteractor: CoursesInteractor
){
    suspend fun addCourse(course: Course) {
        coursesInteractor.addCourse(course)
    }

    suspend fun addCourseToLocal(course: Course) {
        coursesInteractor.addCourseToLocal(course)
    }

    suspend fun loadCourses() {
        coursesInteractor.loadCourses()
    }

    suspend fun loadAllCourses() {
        coursesInteractor.loadAllCourses()
    }

    suspend fun loadCourseDetails(){
        coursesInteractor.loadCourseDetails()
    }

    suspend fun deleteTodo(todo: Int){
        coursesInteractor.deleteTodo(todo)
    }

    suspend fun loadTeachers(course: String) {
        coursesInteractor.loadTeachers(course)
    }
}
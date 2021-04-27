package hu.bme.aut.mystudentapp.interactor

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.CourseDataSource
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import javax.inject.Inject

class CoursesInteractor @Inject constructor(
    private val courseDataSource: CourseDataSource
) {
    suspend fun addCourse(course: Course) {
        courseDataSource.addCourse(course)
    }

    suspend fun addCourseToLocal(course: Course){
        courseDataSource.addCourseToLocal(course)
    }

    suspend fun loadCourses() : List<Course>? {
        return courseDataSource.loadCourses()
    }

    suspend fun loadAllCourses() {
        courseDataSource.loadAllCourses()
    }

    suspend fun loadCourseDetails(){
        courseDataSource.loadCourseDetails()
    }

    suspend fun deleteTodo(todo: Int){
        courseDataSource.deleteTodo(todo)
    }

    suspend fun loadTeachers(course: String) {
        courseDataSource.loadTeachers(course)
    }
}
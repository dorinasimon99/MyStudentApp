package hu.bme.aut.mystudentapp.interactor

import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.CourseDataSource
import hu.bme.aut.mystudentapp.data.model.Course
import javax.inject.Inject

class CoursesInteractor @Inject constructor(
    private val courseDataSource: CourseDataSource
) {
    suspend fun addCourse(course: Course) {
        courseDataSource.addCourse(course)
    }

    suspend fun loadCourses(){
        courseDataSource.loadCourses()
    }
}
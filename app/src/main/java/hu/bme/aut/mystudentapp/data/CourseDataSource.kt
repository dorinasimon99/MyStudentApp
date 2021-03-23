package hu.bme.aut.mystudentapp.data

import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Course
import javax.inject.Inject

class CourseDataSource @Inject constructor(){

    suspend fun loadCourses() {
        //UserDataBackend.getCourses()
        NetworkBackend.getAllCourses()
    }

    suspend fun addCourse(course: Course){
        NetworkBackend.createCourse(course)
        UserDataBackend.addCourse(course)
    }
}
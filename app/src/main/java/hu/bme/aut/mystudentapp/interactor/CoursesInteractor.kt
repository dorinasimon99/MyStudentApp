package hu.bme.aut.mystudentapp.interactor

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.CourseDataSource
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent.Teacher
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

    suspend fun loadAllCourses() : List<Course> {
        return courseDataSource.loadAllCourses()
    }

    suspend fun loadCourseDetails(c: String) : List<Todo>?{
        return courseDataSource.loadCourseDetails(c)
    }

    suspend fun deleteTodo(todo: Int) : List<Todo>?{
        return courseDataSource.deleteTodo(todo)
    }

    suspend fun loadTeachers(course: String) : List<Teacher> {
        return courseDataSource.loadTeachers(course)
    }

    suspend fun addTodo(todo: Todo) : List<Todo>{
        return courseDataSource.addTodo(todo)
    }

    suspend fun saveToLocal(courses: List<Course>){
        courseDataSource.saveCoursesToLocal(courses)
    }
}
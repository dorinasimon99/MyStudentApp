package hu.bme.aut.mystudentapp.data

import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseDataSource @Inject constructor(
    private val courseDao: CourseDao
){

    suspend fun loadCourses() : List<Course>?{
        val courses = courseDao.getCoursesFromLocalDb()
        if(courses.isEmpty()){
            UserDataBackend.getCourses()
        }
        else {
            localCourseToCourse(courses)
        }

        return UserDataBackend.localCourses().value
    }

    suspend fun loadAllCourses() {
        UserDataBackend.getAllCourses()
    }

    suspend fun addCourse(course: Course){
        UserDataBackend.createCourse(course)
        addCourseToLocal(course)
    }

    suspend fun addCourseToLocal(course: Course){
        //UserDataBackend.addCourseToLocal(course)
        val localCourse = LocalCourseEntity(course.id, course.courseCode, course.name, course.credits, course.time)
        courseDao.insert(localCourse)
    }

    suspend fun loadCourseDetails(){
        UserDataBackend.getTodos()
    }

    suspend fun deleteTodo(todo: Int){
        UserDataBackend.deleteTodo(todo)
    }

    private suspend fun localCourseToCourse(localCourses : List<LocalCourseEntity>) {
        for(local in localCourses){
            val course = Course(local.id, local.courseCode, local.name, local.credits, local.time)
            val currentLocals = UserDataBackend.localCourses().value
            if(!currentLocals!!.contains(course)) {
                UserDataBackend.addCourseToLocal(course)
            }
        }
    }
}
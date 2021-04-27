package hu.bme.aut.mystudentapp.data

import hu.bme.aut.mystudentapp.backend.NetworkBackend
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
        //courseDao.deleteAll()
        val courses = courseDao.getCoursesFromLocalDb(UserDataBackend.currentUser.id)
        if(courses.isEmpty()){
            UserDataBackend.getCourses()
            for(local in UserDataBackend.localCourses().value!!){
                courseDao.insert(LocalCourseEntity(local.id, local.courseCode, local.name, local.credits, local.time, UserDataBackend.currentUser.id))
            }
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
        UserDataBackend.addUserCourseData(course)
        courseDao.insert(LocalCourseEntity(course.id, course.courseCode, course.name, course.credits, course.time, UserDataBackend.currentUser.id))

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
            if(currentLocals == null){
                UserDataBackend.addCourseToLocal(course)
            }
            else if(!currentLocals.contains(course)) {
                UserDataBackend.addCourseToLocal(course)
            }
        }
    }

    suspend fun loadTeachers(course: String) {
        UserDataBackend.getTeachers(course)
    }
}
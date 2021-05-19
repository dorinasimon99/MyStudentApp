package hu.bme.aut.mystudentapp.data

import android.util.Log
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.CourseDao
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent.Teacher
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher.Lesson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseDataSource @Inject constructor(
    private val courseDao: CourseDao
){

    suspend fun loadCourses() : List<Course>?{
        val courses = courseDao.getCoursesFromLocalDb(UserDataBackend.currentUser.id)
        if(courses.isEmpty()){
            return UserDataBackend.getCourses()
        }
        else {
            return localCourseToCourse(courses)
        }
    }

    suspend fun loadAllCourses() : List<Course>{
        return UserDataBackend.getAllCourses()
    }

    suspend fun addCourse(course: Course) : List<Course>{
        addCourseToLocal(course)
        if(UserDataBackend.createCourse(course)){
            return UserDataBackend.localCourses()
        }
        return UserDataBackend.localCourses()
    }

    suspend fun addCourseToLocal(course: Course){
        UserDataBackend.addUserCourseData(course)
        courseDao.insert(LocalCourseEntity(course.id, course.courseCode, course.name, course.credits, course.time, UserDataBackend.currentUser.id, ListConverter().listToString(course.teachers)))

    }

    suspend fun loadCourseDetails(c: String) : List<Todo>?{
        return UserDataBackend.getTodos(c)
    }

    suspend fun deleteTodo(todo: Int) : List<Todo>?{
        return UserDataBackend.deleteTodo(todo)
    }

    private fun localCourseToCourse(localCourses : List<LocalCourseEntity>) : List<Course>? {
        val courses = ArrayList<Course>()
        for(local in localCourses){
            val course = Course(local.id, local.courseCode, local.name, local.credits, local.time, null, null, null, ListConverter().stringToList(local.teachersList))
            courses.add(course)
            val currentLocals = UserDataBackend.localCourses()
            if(currentLocals.isEmpty()){
                UserDataBackend.addCourseToLocal(course)
            }
            else if(!currentLocals.contains(course)) {
                UserDataBackend.addCourseToLocal(course)
            }
        }
        return courses
    }

    suspend fun saveCoursesToLocal(courses: List<Course>){
        if(courseDao.getCoursesFromLocalDb(UserDataBackend.currentUser.id).isEmpty()){
            for(local in courses){
                Log.i("CourseDataSource", "insert course to local")
                val teacherList = ListConverter().listToString(local.teachers)
                Log.i("CourseDataSource", "original teacherList: ${local.teachers} new teacherList: $teacherList")
                val localCourse = LocalCourseEntity(local.id, local.courseCode, local.name, local.credits, local.time, UserDataBackend.currentUser.id, teacherList)

                courseDao.insert(localCourse)
            }
        }
    }

    suspend fun loadTeachers(course: String) : List<Teacher> {
        val teachers = courseDao.getCourseTeachers(course)
        if(teachers != null){
            val teachersList = ArrayList<Teacher>()
            for(teacher in ListConverter().stringToList(teachers)!!){
                teachersList.add(Teacher(teacher))
            }
            return teachersList
        }
        return UserDataBackend.getTeachers(course)
    }

    suspend fun deleteCourses(){
        courseDao.deleteAll()
    }

    suspend fun addTodo(todo: Todo) : List<Todo>{
        if(UserDataBackend.createTodo(todo)){
            return UserDataBackend.todos(todo.courseId)
        }
        return UserDataBackend.todos(todo.courseId)
    }

    suspend fun loadTeacherCourses(teachername: String) : List<String>{
        val courses = courseDao.getCoursesFromLocalDb(UserDataBackend.currentUser.id)
        val filteredCourses = ArrayList<String>()
        for(course in courses){
            if(course.teachersList != null && course.teachersList!!.contains(teachername)){
                filteredCourses.add(course.name)
            }
        }
        return filteredCourses
    }

    suspend fun getLessons(teachername: String, courseName: String) : List<Lesson>? {
        return UserDataBackend.getLessons(teachername, courseName)
    }
}
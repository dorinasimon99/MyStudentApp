package hu.bme.aut.mystudentapp.backend

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.UserCourse
import com.amplifyframework.datastore.generated.model.UserData
import hu.bme.aut.mystudentapp.data.model.*
import java.util.*

object UserDataBackend {

    private val TAG = "UserDataBackend"
    private val _isSignedIn = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()
    private val _courses = MutableLiveData<MutableList<Course>>(mutableListOf())
    private val _localCourses = MutableLiveData<MutableList<Course>>(mutableListOf())
    private val _todos = MutableLiveData<MutableList<Todo>>(mutableListOf())
    private val _rates = MutableLiveData<MutableList<TeacherRate>>(mutableListOf())
    private val _comments = MutableLiveData<MutableList<StudentComment>>(mutableListOf())
    lateinit var currentUser : UserData
    var isSignedIn : LiveData<Boolean> = _isSignedIn

    fun setSignedIn(newValue: Boolean) {
        _isSignedIn.postValue(newValue)
    }

    var hasUserData : LiveData<User> = _userData
    fun getUserData() : User? {
        var user : User?
        Log.i("UserDataBackend", "getUserData _userdata: ${_userData.value}")
        if(_userData.value == null){
            Amplify.API.query(
                ModelQuery.list(UserData::class.java),
                { response ->
                    Log.i("UserDataBackend", "Queried")
                    if(response.data != null){
                        for (userData in response.data) {
                            user = User.from(userData)
                            currentUser = userData
                            _userData.postValue(user)
                        }
                    }
                },
                { error -> Log.e("UserDataBackend", "Query failure:", error) }
            )
        }
        return _userData.value
    }
    fun setUserData(user: User) {
        Log.i("UserDataBackend", "Creating user")
        Log.i("UserDataBackend", "Creating user user: $user")

        Amplify.API.mutate(
            ModelMutation.create(user.data),
            { response ->
                Log.i("UserDataBackend", "Created")
                if (response.hasErrors()) {
                    Log.e("UserDataBackend", response.errors.first().message)
                } else {
                    Log.i("UserDataBackend", "Created User data: ${response.data}")
                    _userData.postValue(User.from(response.data))
                    currentUser = response.data
                }
            },
            { error -> Log.e("UserDataBackend", "Create failed:", error) }
        )

    }

    /*fun getCourses() {
        val users = ArrayList<User>()
        users.add(User.from(currentUser))
        val course1 = Course(UUID.randomUUID().toString(), "BMEVIAUAL01",
            "Önálló labor", 5, "Mon 13:00", users, null, null, null, null)
        val course2 = Course(UUID.randomUUID().toString(), "BMEVIAUAC02",
            "Kliensoldali technológiák", 4, "Mon 8:15", users, null, null, null, null)
        val course3 = Course(UUID.randomUUID().toString(), "BMEVIHIAC01",
            "IT Biztonság", 3, "Tues 14:15", users, null, null, null, null)
        val courses = _courses.value
        if(courses != null){
            courses.add(course1)
            courses.add(course2)
            courses.add(course3)
            _courses.notifyObserver()
        } else {
            Log.e(TAG, "getCourses : course collection is null!")
        }

    }*/

    fun getTodos() {
        val todo1 = Todo("Do homework", false)
        val todo2 = Todo("Write essay", false)
        val todo3 = Todo("Study", false)
        val todo4 = Todo("Find a partner", false)
        val todo5 = Todo("Read book", false)
        val todo6 = Todo("Write essay", false)
        val todos = _todos.value
        if(todos != null){
            todos.add(todo1)
            todos.add(todo2)
            todos.add(todo3)
            todos.add(todo4)
            todos.add(todo5)
            todos.add(todo6)
            _todos.notifyObserver()
        } else {
            Log.e(TAG, "getCourses : course collection is null!")
        }
    }

    fun todos() : LiveData<MutableList<Todo>> = _todos

    fun addTodo(todo : Todo){
        val todos = _todos.value
        if(todos != null){
            todos.add(todo)
            _todos.notifyObserver()
        } else {
            Log.e(TAG, "addTodo : todo collection is null!")
        }
    }

    suspend fun deleteTodo(todo: Int){
        _todos.value?.removeAt(todo)
        _todos.notifyObserver()
    }

    fun courses() : LiveData<MutableList<Course>> = _courses
    fun localCourses() : LiveData<MutableList<Course>> = _localCourses
    fun addCourse(c : Course){
        val courses = _courses.value
        if(courses != null){
            courses.add(c)
            _courses.notifyObserver()
        }
        else {
            Log.e(TAG, "addCourse : course collection is null!")
        }
    }

    fun addCourseToLocal(c : Course){
        val courses = _localCourses.value
        if(courses != null){
            courses.add(c)
            _localCourses.notifyObserver()
        } else {
            Log.e(TAG, "addCourseToLocal : localCourse collection is null!")
        }
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }

    fun notifyObserver() {
        _courses.notifyObserver()
    }

    fun getRates() {
        val rate1 = TeacherRate("Teacher1", "CourseName1", "2.5")
        val rate2 = TeacherRate("Teacher1", "CourseName2", "3.5")
        val rate3 = TeacherRate("Teacher1", "CourseName3", "4.1")
        val rate4 = TeacherRate("Teacher1", "CourseName4", "3.9")
        val rate5 = TeacherRate("Teacher1", "CourseName5", "5.0")
        val rate6 = TeacherRate("Teacher1", "CourseName6", "1.5")
        val rates = _rates.value
        if(rates != null){
            rates.add(rate1)
            rates.add(rate2)
            rates.add(rate3)
            rates.add(rate4)
            rates.add(rate5)
            rates.add(rate6)
            _rates.notifyObserver()
        } else {
            Log.e(TAG, "getRates : rates collection is null!")
        }
    }

    fun rates() : LiveData<MutableList<TeacherRate>> = _rates

    fun getComments() {
        val comment1 = StudentComment("Student1", "Comment1")
        val comment2 = StudentComment("Student2", "Comment2")
        val comment3 = StudentComment("Student3", "Comment3")
        val comments = _comments.value
        if(comments != null){
            comments.add(comment1)
            comments.add(comment2)
            comments.add(comment3)
            _comments.notifyObserver()
        } else {
            Log.e(TAG, "getRates : rates collection is null!")
        }
    }

    fun comments() : LiveData<MutableList<StudentComment>> = _comments



    suspend fun createCourse(c: Course) {
        Log.i(TAG, "Creating course")

        Amplify.API.mutate(
            ModelMutation.create(c.data),
            { response ->
                Log.i(TAG, "Course created")
                if(response.hasErrors()){
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created course with name: " + response.data.name)
                }
            },
            { error -> Log.e(TAG, "Course create failed", error)}
        )

        Amplify.API.mutate(
            ModelMutation.create(UserCourseModel(UUID.randomUUID().toString(), currentUser, c.data).data),
            { response ->
                Log.i(TAG, "UserCourse created")
                if(response.hasErrors()){
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created userCourse with course " + response.data.course.name + " and user "+ response.data.user.name)
                }
            },
            { error -> Log.e(TAG, "UserCourse create failed", error) }
        )
    }

    private fun getCourseForUser(courseId: String) : Boolean {
        var isCorrectCourse = false
        Amplify.API.query(
            ModelQuery.get(UserCourse::class.java, courseId),
            { response ->
                Log.i(TAG, "get courses for user")
                if (response.data != null && response.data.course.id == courseId && response.data.user.id == currentUser.id) {
                    isCorrectCourse = true
                }
            },
            { error -> Log.e("UserDataBackend", "Get course for user failed: ", error) }
        )
        return isCorrectCourse
    }

    fun getCourses() {
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i("UserDataBackend", "Courses queried")
                if(response.data != null){
                    for (courseData in response.data) {
                        Log.i("UserDataBackend", "getCourses() response.data: ${response.data}")
                        if (getCourseForUser(courseData.id)) {
                            addCourseToLocal(Course.from(courseData))
                        }
                    }
                }
                else {
                    Log.e("UserDataBackend", "getCourses() : response.data is null")
                }
            },
            { error -> Log.e("UserDataBackend", "Courses query failure:", error) }
        )
    }

    fun getAllCourses() {
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i(TAG, "Queried all courses")
                for (courseData in response.data) {
                    addCourse(Course.from(courseData))
                }
                notifyObserver()
            },
            { error -> Log.e(TAG, "All courses query failure:", error) }
        )
    }
}
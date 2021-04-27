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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*
import kotlin.collections.ArrayList

object UserDataBackend {

    private val TAG = "UserDataBackend"
    private val _isSignedIn = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()
    private val _courses = MutableLiveData<MutableList<Course>>(mutableListOf())
    private val _localCourses = MutableLiveData<MutableList<Course>>(mutableListOf())
    private val _todos = MutableLiveData<MutableList<Todo>>(mutableListOf())
    private val _rates = MutableLiveData<MutableList<TeacherRate>>(mutableListOf())
    private val _comments = MutableLiveData<MutableList<StudentComment>>(mutableListOf())
    private val _teachers = MutableLiveData<MutableList<String>>(mutableListOf())
    lateinit var currentUser: UserData
    var currentUserName: String? = null
    var isSignedIn: LiveData<Boolean> = _isSignedIn

    fun setSignedIn(newValue: Boolean) {
        _isSignedIn.postValue(newValue)
    }

    val hasUserData: LiveData<User> = _userData
    fun getUserData(username: String?) {
        var user : User?
        val responseData = ArrayList<UserData>()
        Log.i("UserDataBackend", "getUserData _userdata: ${_userData.value}")
        Log.i("UserDataBackend", "getUserData local user: $username")
        Amplify.API.query(
            ModelQuery.list(UserData::class.java),
            { response ->
                Log.i("UserDataBackend", "Queried")
                Log.i("UserDataBackend", "response.data: ${response.data}")
                if(response.data != null){
                    for(userData in response.data){
                        if (username != null){
                            if (userData.username == username){
                                user = User.from(userData)
                                currentUser = userData
                                _userData.postValue(user)
                            }
                        }
                    }
                }
            },
            { error -> Log.e("UserDataBackend", "Query failure:", error) }
        )
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

    fun getTodos() {
        val todo1 = Todo("Do homework", false)
        val todo2 = Todo("Write essay", false)
        val todo3 = Todo("Study", false)
        val todo4 = Todo("Find a partner", false)
        val todo5 = Todo("Read book", false)
        val todo6 = Todo("Write essay", false)
        val todos = _todos.value
        if (todos != null) {
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

    fun todos(): LiveData<MutableList<Todo>> = _todos

    fun addTodo(todo: Todo) {
        val todos = _todos.value
        if (todos != null) {
            todos.add(todo)
            _todos.notifyObserver()
        } else {
            Log.e(TAG, "addTodo : todo collection is null!")
        }
    }

    fun deleteTodo(todo: Int) {
        _todos.value?.removeAt(todo)
        _todos.notifyObserver()
    }

    fun courses(): LiveData<MutableList<Course>> {
        return _courses
    }

    fun localCourses(): LiveData<MutableList<Course>> = _localCourses
    fun addCourse(c: Course) {
        val courses = _courses.value
        if (courses != null) {
            courses.add(c)
            //_courses.notifyObserver()
        } else {
            Log.e(TAG, "addCourse : course collection is null!")
        }
    }

    fun addCourseToLocal(c: Course) {
        val courses = _localCourses.value
        if (courses != null) {
            courses.add(c)
            //_localCourses.notifyObserver()
        } else {
            Log.e(TAG, "addCourseToLocal : localCourse collection is null!")
        }
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }

    fun notifyObserver() {
        _courses.notifyObserver()
        _localCourses.notifyObserver()
    }

    fun getRates() {
        val rate1 = TeacherRate("Teacher1", "CourseName1", "2.5")
        val rate2 = TeacherRate("Teacher1", "CourseName2", "3.5")
        val rate3 = TeacherRate("Teacher1", "CourseName3", "4.1")
        val rate4 = TeacherRate("Teacher1", "CourseName4", "3.9")
        val rate5 = TeacherRate("Teacher1", "CourseName5", "5.0")
        val rate6 = TeacherRate("Teacher1", "CourseName6", "1.5")
        val rates = _rates.value
        if (rates != null) {
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

    fun rates(): LiveData<MutableList<TeacherRate>> = _rates

    fun getComments() {
        val comment1 = StudentComment("Student1", "Comment1")
        val comment2 = StudentComment("Student2", "Comment2")
        val comment3 = StudentComment("Student3", "Comment3")
        val comments = _comments.value
        if (comments != null) {
            comments.add(comment1)
            comments.add(comment2)
            comments.add(comment3)
            _comments.notifyObserver()
        } else {
            Log.e(TAG, "getRates : rates collection is null!")
        }
    }

    fun comments(): LiveData<MutableList<StudentComment>> = _comments


    suspend fun createCourse(c: Course) {
        Log.i(TAG, "Creating course")

        Amplify.API.mutate(
            ModelMutation.create(c.data),
            { response ->
                Log.i(TAG, "Course created")
                if (response.hasErrors()) {
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created course with name: " + response.data.name)
                }
            },
            { error -> Log.e(TAG, "Course create failed", error) }
        )
    }

    suspend fun addUserCourseData(c: Course){
        Log.i(TAG, "Add UserCourseData")

        Amplify.API.mutate(
            ModelMutation.create(
                UserCourseModel(
                    UUID.randomUUID().toString(),
                    currentUser,
                    c.data
                ).data
            ),
            { response ->
                Log.i(TAG, "UserCourse created")
                if (response.hasErrors()) {
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(
                        TAG,
                        "Created userCourse with course " + response.data.course.name + " and user " + response.data.user.name
                    )
                }
            },
            { error -> Log.e(TAG, "UserCourse create failed", error) }
        )

        if(currentUser.role == Role.TEACHER.toString()){

            Amplify.API.query(
                ModelQuery.list(CourseData::class.java),
                { response ->
                    Log.i(TAG, "Queried all courses")
                    for (courseData in response.data) {
                        if(courseData.courseCode == c.courseCode){
                            var newTeachers = courseData!!.teachers
                            if(newTeachers == null) newTeachers = ArrayList<String>()
                            newTeachers.add(currentUser.name)

                            val update = c.data.copyOfBuilder()
                                .teachers(newTeachers).build()

                            Amplify.API.mutate(
                                ModelMutation.update(update),
                                { response1 ->
                                    Log.i(TAG, "Course updated")
                                    if (response1.hasErrors()) {
                                        Log.e(TAG, response.errors.first().message)
                                    } else {
                                        Log.i(TAG, "Updated course teachers: " + response1.data.teachers)
                                    }
                                },
                                { error -> Log.e(TAG, "Course update failed", error) }
                            )
                        }
                    }
                },
                { error -> Log.e(TAG, "All courses query failure:", error) }
            )
        }
    }

    fun getCourses() {
        val courses = ArrayList<Course>()
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i("UserDataBackend", "Courses queried")
                if (response.data != null) {
                    for (courseData in response.data) {
                        Amplify.API.query(
                            ModelQuery.list(UserCourse::class.java),
                            { response1 ->
                                Log.i(TAG, "get courses for user")
                                if (response1.data != null){
                                    for(userCourse in response1.data){
                                        if(userCourse.course.id == courseData.id && userCourse.user.id == currentUser.id){
                                            val course = Course.from(courseData)
                                            addCourseToLocal(course)
                                            courses.add(course)
                                        }
                                    }
                                    notifyObserver()
                                }
                            },
                            { error -> Log.e("UserDataBackend", "Get course for user failed: ", error) }
                        )
                    }
                } else {
                    Log.e("UserDataBackend", "getCourses() : response.data is null")
                }
            },
            { error -> Log.e("UserDataBackend", "Courses query failure:", error) }
        )

        Log.i(TAG, "Courses from getCourses(): $courses")
    }

    fun getAllCourses() {
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i(TAG, "Queried all courses")
                for (courseData in response.data) {
                    val course = Course.from(courseData)
                    addCourse(course)
                }
                notifyObserver()
            },
            { error -> Log.e(TAG, "All courses query failure:", error) }
        )
    }

    fun teachers(): LiveData<MutableList<String>> = _teachers

    fun addTeacher(teacher: String) {
        val teachers = _teachers.value
        if (teachers != null) {
            teachers.add(teacher)
            _teachers.notifyObserver()
        } else {
            Log.e(TAG, "addTeachers : teacher collection is null!")
        }
    }

    fun getTeachers(course: String){
        var teachersList = ArrayList<String>()
        val temp = ArrayList<String>()
        Amplify.API.query(
            ModelQuery.list(CourseData::class.java),
            { response ->
                Log.i(TAG, "Queried all courses for teachers list")
                if(response.data != null){
                    for (courseData in response.data) {
                        if(courseData.name == course && courseData.teachers != null){
                            for(teacher in courseData.teachers){
                                //temp.add(teacher)
                                addTeacher(teacher)
                            }
                        }
                    }
                }
            },
            { error -> Log.e(TAG, "All courses for teachers list query failure:", error) }
        )
    }
}
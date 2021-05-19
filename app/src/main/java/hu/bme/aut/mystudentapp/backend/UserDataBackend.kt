package hu.bme.aut.mystudentapp.backend

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.zsmb.rainbowcake.withIOContext
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.*
import hu.bme.aut.mystudentapp.data.model.*
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent.Teacher
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher.Lesson
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


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
    var isSignedIn: LiveData<Boolean> = _isSignedIn

    fun setSignedIn(newValue: Boolean) {
        _isSignedIn.postValue(newValue)
    }

    val hasUserData: LiveData<User> = _userData

    fun userData(newValue: User){
        _userData.postValue(newValue)
    }

    suspend fun getUserData(username: String?) : User? {
        var user : User?
        Log.i("UserDataBackend", "getUserData _userdata: ${_userData.value}")
        Log.i("UserDataBackend", "getUserData local user: $username")

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(UserData::class.java, UserData.USERNAME.eq(username!!)),
                { response ->
                    Log.i("UserDataBackend", "Queried")
                    Log.i("UserDataBackend", "response.data: ${response.data}")
                    if(response.data != null){
                        for(userData in response.data){
                            user = User.from(userData)
                            currentUser = userData
                            //_userData.postValue(user)
                            continuation.resume(user)
                        }
                    } else if(response.hasErrors()){
                        Log.e(TAG, "GetUserData error: ${response.errors}")
                        continuation.resume(null)
                    }
                },
                { error ->
                    Log.e("UserDataBackend", "Query failure:", error)
                    continuation.resume(null)
                }
            )
        }
    }

    suspend fun setUserData(user: User) {
        Log.i("UserDataBackend", "Creating user")
        Log.i("UserDataBackend", "Creating user user: $user")

        return suspendCoroutine {
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
    }

    suspend fun getTodos(cID: String) : List<Todo>? {
        val todos = ArrayList<Todo>()

        if(todos(cID).isNotEmpty()){
            return todos(cID)
        }

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(TodoData::class.java, TodoData.COURSE_ID.eq(cID)),
                {response ->
                    Log.i(TAG, "Todos queried")
                    if(response.data != null){
                        for(todo in response.data){
                            todos.add(Todo.from(todo))
                        }
                        addTodos(todos)
                        continuation.resume(todos)
                    } else if(response.hasErrors()){
                        Log.e(TAG, "GetTodos error: ${response.errors}")
                        continuation.resume(todos)
                    }
                },
                {error ->
                    Log.e(TAG, "Query todos failed:", error)
                    continuation.resume(todos)
                }
            )
        }
    }

    suspend fun createTodo(todo: Todo) : Boolean {
        return suspendCoroutine { continuation ->
            Amplify.API.mutate(
                ModelMutation.create(todo.data),
                {response ->
                    Log.i(TAG, "Todo created")
                    if (response.hasErrors()) {
                        Log.e(TAG, response.errors.first().message)
                        continuation.resume(false)
                    } else {
                        Log.i(TAG, "Created Todo data: ${response.data}")
                        addTodo(Todo.from(response.data))
                        continuation.resume(true)
                    }
                },
                { error ->
                    Log.e(TAG, "Todo create failed:", error)
                    continuation.resume(false)
                }
            )
        }
    }

    fun todos(courseID: String): List<Todo>{
        val todos = ArrayList<Todo>()
        if(_todos.value != null){
            for(todo in _todos.value!!){
                if(todo.courseId == courseID){
                    todos.add(todo)
                }
            }
        }
        return todos
    }

    fun addTodo(todo: Todo) {
        val todos = _todos.value
        if (todos != null) {
            todos.add(todo)
            _todos.notifyObserver()
        } else {
            Log.e(TAG, "addTodo : todo collection is null!")
        }
    }

    fun addTodos(todo: ArrayList<Todo>) {
        val todos = _todos.value
        if (todos != null) {
            todos.clear()
            for(t in todo){
                todos.add(t)
                _todos.notifyObserver()
            }
        } else {
            Log.e(TAG, "addTodo : todo collection is null!")
        }
    }

    suspend fun deleteTodo(todo: Int) : List<Todo>? {
        val todos = _todos.value
        if(todos != null){
            val td = todos.elementAt(todo)
            todos.removeAt(todo)
            _todos.notifyObserver()

            Amplify.API.mutate(
                ModelMutation.delete(td.data),
                { response ->
                    Log.i(TAG, "Todo deleted")
                    if (response.hasErrors()) {
                        Log.e(TAG, response.errors.first().message)
                    } else {
                        Log.i(TAG, "Deleted Todo data: ${response.data}")
                    }
                },
                { error ->
                    Log.e(TAG, "Todo delete failed: ", error)
                }
            )
            return todos
        }
        return todos
    }

    fun courses(): List<Course> {
        val courses = ArrayList<Course>()
        if(_courses.value != null){
            courses.addAll(_courses.value!!)
        }
        return courses
    }

    fun localCourses(): List<Course>{
        val localCourses = ArrayList<Course>()
        if(_localCourses.value != null){
            localCourses.addAll(_localCourses.value!!)
        }
        return localCourses
    }

    fun addCourse(c: Course) {
        val courses = _courses.value
        if (courses != null && !courses.contains(c)) {
            courses.add(c)
            _courses.notifyObserver()
        } else {
            Log.e(TAG, "addCourse : course collection is null!")
        }
    }

    fun addCourseToLocal(c: Course) {
        val courses = _localCourses.value
        if (courses != null && !courses.contains(c)) {
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
        _localCourses.notifyObserver()
        _teachers.notifyObserver()
        _todos.notifyObserver()
    }

    suspend fun createCourse(c: Course) : Boolean {
        Log.i(TAG, "Creating course")

        return suspendCoroutine { continuation ->
            Amplify.API.mutate(
                ModelMutation.create(c.data),
                { response ->
                    Log.i(TAG, "Course created")
                    if (response.hasErrors()) {
                        Log.e(TAG, response.errors.first().message)
                        continuation.resume(false)
                    } else {
                        Log.i(TAG, "Created course with name: " + response.data.name)
                        addCourse(Course.from(response.data))
                        addCourseToLocal(Course.from(response.data))
                        continuation.resume(true)
                    }
                },
                { error ->
                    Log.e(TAG, "Course create failed", error)
                    continuation.resume(false)
                }
            )
        }
    }

    fun addUserCourseData(c: Course){
        Log.i(TAG, "Add UserCourseData")

        Amplify.API.mutate(
            ModelMutation.create(
                UserCourseModel(
                    UUID.randomUUID().toString(),
                    currentUser,
                    c.data,
                    "",
                    0,
                    -1
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
                ModelQuery.list(CourseData::class.java, CourseData.COURSE_CODE.eq(c.courseCode)),
                { response ->
                    Log.i(TAG, "Queried all courses")
                    for (courseData in response.data) {
                        var newTeachers = courseData!!.teachers
                        if (newTeachers == null) newTeachers = ArrayList<String>()
                        if (!newTeachers.contains(currentUser.name)) {
                            newTeachers.add(currentUser.name)
                        }

                        val update = c.data.copyOfBuilder()
                            .teachers(newTeachers).build()

                        Amplify.API.mutate(
                            ModelMutation.update(update),
                            { response1 ->
                                Log.i(TAG, "Course updated")
                                if (response1.hasErrors()) {
                                    Log.e(TAG, response.errors.first().message)
                                } else {
                                    Log.i(
                                        TAG,
                                        "Updated course teachers: " + response1.data.teachers
                                    )
                                }
                            },
                            { error -> Log.e(TAG, "Course update failed", error) }
                        )
                    }
                },
                { error -> Log.e(TAG, "All courses query failure:", error) }
            )
        }
    }

    suspend fun getCourses() : List<Course>? {
        val localCourses = ArrayList<Course>()
        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(UserCourse::class.java),
                { response ->
                    Log.i(TAG, "get courses for user: ${currentUser.name}")
                    if (response.data != null){
                        for(userCourse in response.data){
                            if(userCourse.user.id == currentUser.id){
                                val course = Course.from(userCourse.course)
                                localCourses.add(course)
                                addCourseToLocal(course)
                            }
                        }
                        continuation.resume(localCourses)
                    }
                    else Log.e(TAG, "response.data is null in getCourses()")
                },
                { error ->
                    Log.e("UserDataBackend", "Get course for user failed: ", error)
                    continuation.resume(null)
                }
            )
        }
    }

    suspend fun getAllCourses() : List<Course> {
        val allCourses = ArrayList<Course>()

        if(courses().isNotEmpty()){
            return courses()
        }

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(CourseData::class.java),
                { response ->
                    Log.i(TAG, "Queried all courses")
                    if(response.data != null){
                        for (courseData in response.data) {
                            val course = Course.from(courseData)
                            allCourses.add(course)
                            addCourse(course)
                        }
                        notifyObserver()
                        continuation.resume(allCourses)
                    } else if(response.hasErrors()){
                        Log.e(TAG, "GetAllCourses error: ${response.errors}")
                        continuation.resume(allCourses)
                    }
                },
                { error ->
                    Log.e(TAG, "All courses query failure:", error)
                    continuation.resume(allCourses)
                }
            )
        }
    }

    fun addTeachers(teacher: MutableList<String>) {
        val teachers = _teachers.value
        if (teachers != null) {
            teachers.clear()
            for(t in teacher){
                teachers.add(t)
                _teachers.notifyObserver()
            }
        } else {
            Log.e(TAG, "addTeachers : teacher collection is null!")
        }
    }

    suspend fun getTeachers(course: String) : List<Teacher> {
        val teachers = ArrayList<Teacher>()

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(CourseData::class.java, CourseData.NAME.eq(course)),
                { response ->
                    Log.i(TAG, "Queried all courses for teachers list")
                    if(response.data != null){
                        for (courseData in response.data) {
                            for(teacher in courseData.teachers){
                                teachers.add(Teacher(teacher))
                            }
                            addTeachers(courseData.teachers)
                        }
                        continuation.resume(teachers)
                    } else if(response.hasErrors()){
                        Log.e(TAG, "GetTeachers error: ${response.errors}")
                        continuation.resume(teachers)
                    }
                },
                { error ->
                    Log.e(TAG, "All courses for teachers list query failure:", error)
                    continuation.resume(teachers)
                }
            )
        }
    }

    suspend fun getRates(teachername: String) : List<TeacherRate>? {
        val allRates = ArrayList<TeacherRate>()

        if(!rates(teachername).isNullOrEmpty()){
            return rates(teachername)
        }

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(UserCourse::class.java),
                { response ->
                    Log.i(TAG, "Rates queried")
                    if(response.data != null){
                        for (courseData in response.data) {
                            if(courseData.rating.isNotEmpty() && courseData.user.name == teachername){
                                allRates.add(TeacherRate(courseData.user.name, courseData.course.name, courseData.rating))
                            }
                        }
                        addRates(allRates)
                        continuation.resume(allRates)
                    } else if(response.hasErrors()){
                        Log.e(TAG, "GetRates error: ${response.errors}")
                        continuation.resume(allRates)
                    }
                },
                { error ->
                    Log.e(TAG, "All courses for teachers list query failure:", error)
                    continuation.resume(allRates)
                }
            )
        }
    }

    suspend fun createRate(rate: TeacherRate) : Boolean{
        return suspendCoroutine {continuation ->
            Amplify.API.query(
                ModelQuery.list(UserCourse::class.java),
                { response ->
                    if(response.data != null){
                        for(userCourse in response.data){
                            if(userCourse.course.name == rate.courseName && userCourse.user.name == rate.teacherName){
                                var newRating = ""
                                var newRatingNum = 0
                                if(userCourse.rating.isEmpty()){
                                    newRating = rate.rating
                                } else {
                                    val multiply = userCourse.rating.toDouble() * userCourse.ratingNum
                                    Log.i(TAG, "multiplication: $multiply")
                                    val add = multiply + rate.rating.toDouble()
                                    Log.i(TAG, "add: $add")
                                    newRatingNum = userCourse.ratingNum + 1
                                    Log.i(TAG, "ratingNum: $newRatingNum")
                                    val divide = add / newRatingNum
                                    Log.i(TAG, "divide: $divide")
                                    val nf = NumberFormat.getNumberInstance(Locale.ENGLISH)
                                    nf.maximumFractionDigits = 1
                                    newRating = nf.format(divide)
                                }
                                val userCourseData = userCourse.copyOfBuilder()
                                    .rating(newRating)
                                    .ratingNum(newRatingNum)
                                    .build()

                                Amplify.API.mutate(
                                    ModelMutation.update(userCourseData),
                                    { response1 ->
                                        Log.i(TAG, "UserCourse updated")
                                        if (response1.hasErrors()) {
                                            Log.e(TAG, response.errors.first().message)
                                            continuation.resume(false)
                                        } else {
                                            Log.i(TAG, "Updated usercourse rating: ${response1.data.user.name} ${response1.data.course.name} " +
                                                    response1.data.rating
                                            )
                                            continuation.resume(true)
                                        }
                                    },
                                    { error ->
                                        Log.e(TAG, "Course update failed", error)
                                        continuation.resume(false)
                                    }
                                )
                                updateRate(rate, newRating)
                                break;
                            }

                        }
                    } else if(response.hasErrors()){
                        Log.e(TAG, "Query usercourses for createRate error: ${response.errors}")
                        continuation.resume(false)
                    }
                },
                { error ->
                    Log.e(TAG, "List usercourses for createRate error: ", error)
                    continuation.resume(false)
                }
            )
        }



    }

    fun rates(teachername: String): List<TeacherRate> {
        val rates = ArrayList<TeacherRate>()
        if(_rates.value != null){
            for(rate in _rates.value!!){
                if(rate.teacherName == teachername) rates.add(rate)
            }
        }
        return rates
    }

    fun addRates(allRates: ArrayList<TeacherRate>){
        val rates = _rates.value
        if(rates != null){
            rates.clear()
            for(rate in allRates){
                rates.add(rate)
                _rates.notifyObserver()
            }
        } else {
            Log.e(TAG, "addRates : rates collection is null!")
        }
    }
    fun updateRate(rate: TeacherRate, newRating: String){
        val rates = _rates.value
        var updated = false
        if(rates != null){
            var index = -1;
            for(r in rates){
                if(r.courseName == rate.courseName && r.teacherName == rate.teacherName){
                    index = rates.indexOf(r)
                }
            }
            if(index > -1){
                rates.removeAt(index);
                Log.i(TAG, "$index")
                rates.add(index, TeacherRate(rate.teacherName, rate.courseName, newRating))
                updated = true
            }
            if(!updated){
                rates.add(TeacherRate(rate.teacherName, rate.courseName, newRating))
            }
            _rates.notifyObserver()
        } else {
            Log.e(TAG, "addRate : rates collection is null!")
        }
    }

    suspend fun getComments(teachername: String) : List<StudentComment> {
        val comments = ArrayList<StudentComment>()

        if(!comments(teachername).isNullOrEmpty()){
            return comments(teachername)
        }

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(CommentData::class.java, CommentData.TEACHERNAME.eq(teachername)),
                {response ->
                    if(response.data != null){
                        for(comment in response.data){
                            val comm = StudentComment.from(comment)
                            addComment(comm)
                            comments.add(comm)
                        }
                        continuation.resume(comments)
                    } else if(response.hasErrors()){
                        Log.e(TAG, "GetComments error: ${response.errors}")
                        continuation.resume(comments)
                    }
                },
                {error ->
                    Log.e(TAG, "Comment query failed:", error)
                    continuation.resume(comments)
                }
            )
        }
    }

    fun comments(teachername: String): List<StudentComment> {
        val comments = ArrayList<StudentComment>()
        if(_comments.value != null){
            for(comment in _comments.value!!){
                if(comment.teacherName == teachername) comments.add(comment)
            }
        }
        return comments
    }

    fun addComment(c: StudentComment){
        val comments = _comments.value
        if(comments != null){
            comments.add(c)
            _comments.notifyObserver()
        } else {
            Log.e(TAG, "addComments : comments collection is null!")
        }
    }

    suspend fun createComment(comment: StudentComment) : Boolean {
        return suspendCoroutine { continuation ->
            Amplify.API.mutate(
                ModelMutation.create(comment.data),
                { response ->
                    if(response.hasErrors()){
                        Log.e(TAG, response.errors.first().message)
                        continuation.resume(false)
                    }
                    else {
                        Log.i(TAG, "Comment created: ${response.data}")
                        addComment(StudentComment.from(response.data))
                        continuation.resume(true)
                    }
                },
                { error ->
                    Log.e(TAG, "Create comment failed:", error)
                    continuation.resume(false)
                }
            )
        }
    }

    suspend fun getLessons(teachername: String, courseName: String) : List<Lesson>? {
        val lessons = ArrayList<Lesson>()

        return suspendCoroutine { continuation ->
            Amplify.API.query(
                ModelQuery.list(UserCourse::class.java),
                { response ->
                    if(response.data != null){
                        for (userData in response.data){
                            if(userData.user.name == teachername && userData.course.name == courseName){
                                for(lesson in userData.lessons){
                                    lessons.add(Lesson(lesson))
                                }
                                continuation.resume(lessons)
                                break;
                            }
                        }
                    }
                    else if(response.hasErrors()){
                        Log.e(TAG, "UserCourse query response has errors: ${response.errors}")
                        continuation.resume(null)
                    }
                },
                { error ->
                    Log.e(TAG, "UserCourse query for lessons failed", error)
                    continuation.resume(null)
                }
            )
        }
    }
}
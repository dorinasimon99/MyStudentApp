package hu.bme.aut.mystudentapp.backend

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.CourseData
import com.amplifyframework.datastore.generated.model.UserData

object Data {

    private const val TAG = "Data"

    private val _isSignedIn = MutableLiveData<Boolean>()
    private val _userData = MutableLiveData<User>()
    private val _courses = MutableLiveData<MutableList<Course>>(mutableListOf())
    var isSignedIn: LiveData<Boolean> = _isSignedIn

    fun setSignedIn(newValue: Boolean) {
        _isSignedIn.postValue(newValue)
    }

    var hasUserData: LiveData<User> = _userData
    fun getUserData() : User? {
        var user : User?
        if(_userData.value == null){
            Amplify.API.query(
                ModelQuery.list(UserData::class.java),
                { response ->
                    Log.i(TAG, "Queried")
                    for(userData in response.data){
                        user = User.from(userData)
                        _userData.postValue(user)
                    }
                },
                { error -> Log.e(TAG, "Query failure", error) }
            )
        }
        return _userData.value
    }
    fun setUserData(user: User){
        Log.i(TAG, "Creating user")

        Amplify.API.mutate(
            ModelMutation.create(user.data),
            { response ->
                Log.i(TAG, "Created")
                if (response.hasErrors()) {
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created User data: " + response.data)
                    _userData.postValue(User.from(response.data))
                    //Backend.updateUserData(true)
                }
            },
            { error -> Log.e(TAG, "Create failed", error) }
        )
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        this._courses.notifyObserver()
    }

    fun courses() : LiveData<MutableList<Course>>  = _courses
    fun addCourse(c : Course){
        val courses = _courses.value
        if(courses != null){
            courses.add(c)
            _courses.notifyObserver()
        } else {
            Log.e(TAG, "addCourse : course collection is null !!")
        }
    }

    data class User(val id: String, val name: String?, val role: String, val grade: Int?){
        val data : UserData
        get() = UserData.builder()
            .role(this.role)
            .id(this.id)
            .name(this.name)
            .grade(this.grade)
            .build()

        companion object{
            fun from(userData : UserData) : User {
                val result = User(userData.id, userData.name, userData.role, userData.grade)
                // some additional code will come here later
                return result
            }
        }
    }

    data class Course(val id: String, val courseCode: String, val name: String, val credits: Int){
        val data : CourseData
        get() = CourseData.builder()
            .courseCode(this.courseCode)
            .name(this.name)
            .credits(this.credits)
            .id(this.id)
            .build()

        companion object{
            fun from(courseData : CourseData) : Course {
                val result = Course(courseData.id, courseData.courseCode, courseData.name, courseData.credits)
                // some additional code will come here later
                return result
            }
        }
    }
}
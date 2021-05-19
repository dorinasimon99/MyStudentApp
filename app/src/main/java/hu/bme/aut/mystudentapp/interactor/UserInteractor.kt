package hu.bme.aut.mystudentapp.interactor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.data.CourseDataSource
import hu.bme.aut.mystudentapp.data.NetworkDataSource
import hu.bme.aut.mystudentapp.data.UserDataSource
import hu.bme.aut.mystudentapp.data.model.*
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher.Lesson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userDatasource: UserDataSource,
    private val networkDataSource: NetworkDataSource,
    private val courseDataSource: CourseDataSource
){
    suspend fun getUserData(username: String?) : User?{
        return userDatasource.getUserData(username)
    }

    suspend fun isSignedIn() : LiveData<Boolean> {
        return userDatasource.isSignedIn()
    }

    suspend fun setSignedIn(newValue: Boolean) {
        userDatasource.setSignedIn(newValue)
    }

    var hasUserData : LiveData<User> = userDatasource.hasUserData

    suspend fun setUserData(user: User){
        userDatasource.setUserData(user)
    }

    suspend fun initialize(applicationContext: Context) {
        networkDataSource.initialize(applicationContext)
    }

    suspend fun signOut() {
        networkDataSource.signOut()
    }

    suspend fun signIn(username: String, password: String) : String {
        val success = networkDataSource.signIn(username, password)
        if(success == ""){
            userDatasource.setLocalUser(username)
            courseDataSource.deleteCourses()
        }
        return success
    }

    suspend fun loadRates(teachername: String) : List<TeacherRate>? {
        return userDatasource.loadRates(teachername)
    }

    suspend fun loadComments(teachername: String) : List<StudentComment>?{
        return userDatasource.loadComments(teachername)
    }

    suspend fun getLocalUser() : LocalUserData? {
        return userDatasource.getLocalUser()
    }

    suspend fun signUp(username: String, password: String, email: String) : String? {
        return networkDataSource.signUp(username, password, email)
    }

    suspend fun confirmSignUp(username: String, confirmCode: String, password: String) : String{
        return networkDataSource.confirmSignUp(username, confirmCode, password)
    }

    suspend fun loadCourses(teachername: String) : List<String>{
        return courseDataSource.loadTeacherCourses(teachername)
    }

    suspend fun addRate(rate: TeacherRate) : List<TeacherRate>?{
        return userDatasource.addRate(rate)
    }

    suspend fun addComment(comment: StudentComment) : List<StudentComment>?{
        return userDatasource.addComment(comment)
    }

    suspend fun getLessons(teachername: String, courseName: String) : List<Lesson>? {
        return courseDataSource.getLessons(teachername, courseName)
    }
}
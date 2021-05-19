package hu.bme.aut.mystudentapp.data

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.UserDataDao
import hu.bme.aut.mystudentapp.data.model.*
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor(
    private val userDataDao: UserDataDao
){
    suspend fun isSignedIn() : LiveData<Boolean> {
        return UserDataBackend.isSignedIn
    }

    suspend fun setSignedIn(newValue: Boolean) {
        UserDataBackend.setSignedIn(newValue)
    }

    var hasUserData : LiveData<User> = UserDataBackend.hasUserData

    suspend fun getUserData(username: String?) : User? {
        return UserDataBackend.getUserData(username)
    }

    suspend fun setUserData(user: User){
        val addUser = LocalUserData(UUID.randomUUID().toString(), user.username)
        val localUser = userDataDao.getLocalUser()
        if(localUser == null){
            userDataDao.addLocalUserData(addUser)
        }
        else {
            userDataDao.changeLocalUser(addUser.id, addUser.username)
        }
        UserDataBackend.setUserData(user)
    }

    suspend fun loadRates(teachername: String) : List<TeacherRate>? {
        return UserDataBackend.getRates(teachername)
    }

    suspend fun loadComments(teachername: String) : List<StudentComment>?{
        return UserDataBackend.getComments(teachername)
    }

    suspend fun getLocalUser() : LocalUserData? {
        return userDataDao.getLocalUser()
    }

    suspend fun setLocalUser(username: String) {
        val addUser = LocalUserData(UUID.randomUUID().toString(), username)
        val localUser = userDataDao.getLocalUser()
        if(localUser == null){
            userDataDao.addLocalUserData(addUser)
        }
        else {
            userDataDao.changeLocalUser(addUser.id, addUser.username)
        }
    }
    private suspend fun changeLocal(newUser: LocalUserData) = withIOContext{
        userDataDao.changeLocalUser(newUser.id, newUser.username)
    }

    suspend fun addRate(rate: TeacherRate) : List<TeacherRate>?{
        return if(UserDataBackend.createRate(rate)){
            UserDataBackend.rates(rate.teacherName)
        }else UserDataBackend.rates(rate.teacherName)
    }

    suspend fun addComment(comment: StudentComment) : List<StudentComment>?{
        return if(UserDataBackend.createComment(comment)){
            UserDataBackend.comments(comment.teacherName)
        } else UserDataBackend.comments(comment.teacherName)
    }
}
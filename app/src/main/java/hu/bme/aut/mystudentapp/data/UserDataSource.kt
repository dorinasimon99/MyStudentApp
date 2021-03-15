package hu.bme.aut.mystudentapp.data

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import hu.mystudentapp.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor(){
    var isSignedIn : LiveData<Boolean> = UserDataBackend.isSignedIn

    fun setSignedIn(newValue: Boolean){
        UserDataBackend.setSignedIn(newValue)
    }

    var hasUserData : LiveData<User> = UserDataBackend.hasUserData

    fun getUserData() : User? {
        return UserDataBackend.getUserData()
    }

    fun setUserData(user: User){
        UserDataBackend.setUserData(user)
    }



}
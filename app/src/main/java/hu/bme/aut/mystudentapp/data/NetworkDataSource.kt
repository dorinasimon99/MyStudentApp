package hu.bme.aut.mystudentapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.dataAnnotationObject.UserDataDao
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(){
    suspend fun signOut() {
        NetworkBackend.signOut()
    }

    suspend fun signIn(username: String, password: String) : String {
        return NetworkBackend.signIn(username, password)
    }

    suspend fun initialize(applicationContext: Context){
        NetworkBackend.initialize(applicationContext)
    }

    suspend fun updateUserData() : Boolean {
        return NetworkBackend.userData()
    }

    suspend fun signUp(username: String, password: String, email: String) : String? {
        return NetworkBackend.signUp(username, password, email)
    }

    suspend fun confirmSignUp(username: String, confirmCode: String, password: String) : String {
        return NetworkBackend.confirmSignUp(username, confirmCode, password)
    }

}
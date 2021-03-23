package hu.bme.aut.mystudentapp.interactor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.NetworkDataSource
import hu.bme.aut.mystudentapp.data.UserDataSource
import hu.bme.aut.mystudentapp.data.model.User
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userDatasource: UserDataSource,
    private val networkDataSource: NetworkDataSource
){
    suspend fun getUserData(): User?{
        return userDatasource.getUserData()
    }

    var isSignedIn: LiveData<Boolean> = userDatasource.isSignedIn

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

    suspend fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        networkDataSource.handleWebUISignInResponse(requestCode, resultCode, data)
    }

    suspend fun signOut() {
        networkDataSource.signOut()
    }

    suspend fun signIn(callingActivity: Activity) {
        networkDataSource.signIn(callingActivity)
        userDatasource.setSignedIn(networkDataSource.updateUserData())
    }
}
package hu.bme.aut.mystudentapp.interactor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.data.NetworkDataSource
import hu.bme.aut.mystudentapp.data.UserDataSource
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userDatasource: UserDataSource,
    private val networkDataSource: NetworkDataSource
){
    suspend fun getUserData(username: String?) {
        userDatasource.getUserData(username)
    }

    suspend fun isSignedIn() : Boolean {
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

    /*suspend fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        networkDataSource.handleWebUISignInResponse(requestCode, resultCode, data)
    }*/

    suspend fun signOut() {
        networkDataSource.signOut()
    }

    suspend fun signIn(username: String, password: String) {
        networkDataSource.signIn(username, password)
        userDatasource.setLocalUser(username)
        //userDatasource.setSignedIn(networkDataSource.updateUserData())
    }

    suspend fun loadRates() {
        userDatasource.loadRates()
    }

    suspend fun loadComments(){
        userDatasource.loadComments()
    }

    suspend fun getLocalUser() : LocalUserData? {
        return userDatasource.getLocalUser()
    }

    suspend fun signUp(username: String, password: String, email: String){
        networkDataSource.signUp(username, password, email)
    }

    suspend fun confirmSignUp(username: String, confirmCode: String, password: String){
        networkDataSource.confirmSignUp(username, confirmCode, password)
    }

    // pass the data from web redirect to Amplify libs
    /*fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("UserInteractor", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }*/
}
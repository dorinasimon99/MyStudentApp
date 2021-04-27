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
class NetworkDataSource @Inject constructor(
    private val userDataDao: UserDataDao
){
    suspend fun signOut() {
        NetworkBackend.signOut()
        //UserDataBackend.currentUserName = userDataDao.getLocalUser()?.username.toString()
        //UserDataBackend.setSignedIn(false)
    }

    suspend fun signIn(username: String, password: String) {
        NetworkBackend.signIn(username, password)
        //UserDataBackend.setSignedIn(true)
    }

    suspend fun initialize(applicationContext: Context){
        NetworkBackend.initialize(applicationContext)
    }

    suspend fun updateUserData() : Boolean? {
        return NetworkBackend.userData()
    }

    suspend fun signUp(username: String, password: String, email: String){
        NetworkBackend.signUp(username, password, email)
    }

    suspend fun confirmSignUp(username: String, confirmCode: String, password: String){
        NetworkBackend.confirmSignUp(username, confirmCode, password)
    }

    // pass the data from web redirect to Amplify libs
    /*fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("NetworkDataSource", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }*/

}
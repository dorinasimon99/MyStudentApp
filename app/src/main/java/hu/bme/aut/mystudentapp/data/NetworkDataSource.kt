package hu.bme.aut.mystudentapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.data.model.Role
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(){
    suspend fun signOut() {
        NetworkBackend.signOut()
    }

    suspend fun signIn(callingActivity: Activity) {
        NetworkBackend.signIn(callingActivity)
    }

    /*suspend fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        NetworkBackend.handleWebUISignInResponse(requestCode, resultCode, data)
    }*/

    suspend fun initialize(applicationContext: Context){
        NetworkBackend.initialize(applicationContext)
    }

    suspend fun updateUserData() : Boolean {
        return NetworkBackend.userData()
    }

    // pass the data from web redirect to Amplify libs
    /*fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("NetworkDataSource", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }*/

}
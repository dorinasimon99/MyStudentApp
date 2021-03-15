package hu.bme.aut.mystudentapp.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(){
    fun signOut() {
        NetworkBackend.signOut()
    }

    fun signIn(callingActivity: Activity) {
        NetworkBackend.signIn(callingActivity)
    }

    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        NetworkBackend.handleWebUISignInResponse(requestCode, resultCode, data)
    }

    fun initialize(applicationContext: Context){
        NetworkBackend.initialize(applicationContext)
    }

    fun updateUserData() : Boolean {
        return NetworkBackend.userData()
    }

}
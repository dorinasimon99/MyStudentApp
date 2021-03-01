package hu.bme.aut.mystudentapp.backend

import android.app.Application

class MyStudentApp : Application(){
    override fun onCreate() {
        super.onCreate()

        // initialize Amplify when application is starting
        Backend.initialize(applicationContext)
    }
}
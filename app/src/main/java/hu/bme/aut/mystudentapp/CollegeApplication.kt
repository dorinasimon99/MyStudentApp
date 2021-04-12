package hu.bme.aut.mystudentapp

import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.di.ApplicationModule
import hu.bme.aut.mystudentapp.di.DaggerAppComponent

class CollegeApplication : RainbowCakeApplication() {

    override lateinit var injector: RainbowCakeComponent

    override fun setupInjector() {
        injector = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        NetworkBackend.initialize(applicationContext)
    }
}
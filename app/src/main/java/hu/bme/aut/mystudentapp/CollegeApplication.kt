package hu.bme.aut.mystudentapp

import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.dagger.BuildConfig
import co.zsmb.rainbowcake.dagger.RainbowCakeApplication
import co.zsmb.rainbowcake.dagger.RainbowCakeComponent
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.di.DaggerAppComponent

class CollegeApplication : RainbowCakeApplication() {

    override lateinit var injector: RainbowCakeComponent

    override fun setupInjector() {
        injector = DaggerAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()

        NetworkBackend.initialize(applicationContext)
    }
}
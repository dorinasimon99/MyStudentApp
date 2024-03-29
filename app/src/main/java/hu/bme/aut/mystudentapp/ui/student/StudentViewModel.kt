package hu.bme.aut.mystudentapp.ui.student

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import kotlinx.coroutines.flow.collect
import okhttp3.internal.notifyAll
import java.lang.Exception
import javax.inject.Inject

class StudentViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<StudentScreenViewState>(StudentBegin){

    fun getStudent() = execute {
        viewState = StudentBegin
        val localUser = try {
            userInteractor.getLocalUser()
        } catch (e: Exception){
            return@execute
        }
        var userData : User? = null
        try {
            userData = User.from(UserDataBackend.currentUser)
        } catch (e: Exception){
            viewState = StudentError
        }
        viewState = StudentInitial(userData)
    }
}
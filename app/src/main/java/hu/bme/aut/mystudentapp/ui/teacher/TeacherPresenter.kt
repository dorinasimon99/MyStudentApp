package hu.bme.aut.mystudentapp.ui.teacher

import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import javax.inject.Inject

class TeacherPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getUserData(username: String?) {
        return userInteractor.getUserData(username)
    }

    suspend fun getLocalUser(): LocalUserData? {
        return userInteractor.getLocalUser()
    }

    suspend fun loadRates(){
        userInteractor.loadRates()
    }

    suspend fun loadComments(){
        userInteractor.loadComments()
    }
}
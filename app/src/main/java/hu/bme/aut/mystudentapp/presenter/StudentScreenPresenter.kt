package hu.bme.aut.mystudentapp.presenter

import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import javax.inject.Inject

class StudentScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getUserData(): User?{
        return userInteractor.getUserData()
    }
}
package hu.bme.aut.mystudentapp.ui.teacher

import hu.bme.aut.mystudentapp.interactor.UserInteractor
import javax.inject.Inject

class TeacherPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun loadRates(){
        userInteractor.loadRates()
    }

    suspend fun loadComments(){
        userInteractor.loadComments()
    }
}
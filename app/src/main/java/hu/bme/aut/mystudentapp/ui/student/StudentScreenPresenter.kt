package hu.bme.aut.mystudentapp.ui.student

import androidx.lifecycle.LiveData
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import javax.inject.Inject

class StudentScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getUserData(): User?{
        return userInteractor.getUserData()
    }
}
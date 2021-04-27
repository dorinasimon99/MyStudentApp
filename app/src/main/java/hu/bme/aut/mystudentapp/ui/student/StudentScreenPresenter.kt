package hu.bme.aut.mystudentapp.ui.student

import androidx.lifecycle.LiveData
import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudentScreenPresenter @Inject constructor(
    private val userInteractor: UserInteractor
) {
    suspend fun getUserData(username: String?) {
        return userInteractor.getUserData(username)
    }

    suspend fun getLocalUser(): LocalUserData? {
        return userInteractor.getLocalUser()
    }
}
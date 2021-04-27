package hu.bme.aut.mystudentapp.ui.teacher

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.student.StudentBegin
import hu.bme.aut.mystudentapp.ui.student.StudentError
import hu.bme.aut.mystudentapp.ui.student.StudentInitial
import java.lang.Exception
import javax.inject.Inject

class TeacherViewModel @Inject constructor(
    private val teacherPresenter: TeacherPresenter
) : RainbowCakeViewModel<TeacherViewState>(TeacherBegin) {

    fun getTeacher() = execute {
        viewState = TeacherBegin
        val localUser = try {
            teacherPresenter.getLocalUser()
        } catch (e: Exception){
            return@execute
        }
        var userData : User? = null
        try {
            userData = User.from(UserDataBackend.currentUser)//teacherPresenter.getUserData(localUser?.username)
        } catch (e: Exception){
            viewState = TeacherError
        }
        viewState = TeacherInitial(userData)
    }
}
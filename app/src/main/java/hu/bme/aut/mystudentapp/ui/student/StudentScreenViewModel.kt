package hu.bme.aut.mystudentapp.ui.student

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import java.lang.Exception
import javax.inject.Inject

class StudentScreenViewModel @Inject constructor(
    private val studentScreenPresenter: StudentScreenPresenter
) : RainbowCakeViewModel<StudentScreenViewState>(StudentBegin){

    fun getStudent() = execute {
        viewState = StudentBegin
        viewState = try {
            val student = studentScreenPresenter.getUserData()
            StudentInitial(student)
        } catch (e: Exception){
            StudentError
        }
    }
}
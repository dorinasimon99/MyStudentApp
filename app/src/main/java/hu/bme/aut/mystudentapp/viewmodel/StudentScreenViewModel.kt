package hu.bme.aut.mystudentapp.viewmodel

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.presenter.StudentScreenPresenter
import hu.bme.aut.mystudentapp.viewstate.StudentBegin
import hu.bme.aut.mystudentapp.viewstate.StudentError
import hu.bme.aut.mystudentapp.viewstate.StudentInitial
import hu.bme.aut.mystudentapp.viewstate.StudentScreenViewState
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
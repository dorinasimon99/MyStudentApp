package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.ui.teacher.TeacherPresenter
import java.lang.Exception
import javax.inject.Inject

class TeacherRatesViewModel @Inject constructor(
    private val teacherPresenter: TeacherPresenter
) : RainbowCakeViewModel<TeacherRatesViewState>(TeacherRatesInitial) {
    fun load() = execute {
        viewState = try {
            teacherPresenter.loadRates()
            teacherPresenter.loadComments()
            TeacherRatesInitial
        } catch (e: Exception){
            TeacherRatesError
        }
    }
}
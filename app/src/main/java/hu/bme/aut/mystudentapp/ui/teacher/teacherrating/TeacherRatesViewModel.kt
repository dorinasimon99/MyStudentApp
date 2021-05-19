package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.StudentComment
import hu.bme.aut.mystudentapp.data.model.TeacherRate
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import hu.bme.aut.mystudentapp.ui.teacher.TeacherError
import java.lang.Exception
import javax.inject.Inject

class TeacherRatesViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<TeacherRatesViewState>(TeacherRatesInitial) {
    fun load(teachername: String) = execute {
        viewState = TeacherRatesInitial
        viewState = try {
            TeacherRatesAndCommentsLoaded(userInteractor.loadRates(teachername),
            userInteractor.loadComments(teachername))

        } catch (e: Exception){
            TeacherRatesError
        }
        viewState =  try {
            TeacherCoursesLoaded(userInteractor.loadCourses(teachername))
        } catch (e: Exception){
            TeacherRatesError
        }
    }

    fun addRating(rate: TeacherRate) = execute {
        viewState = try {
            TeacherRatesUpdated(userInteractor.addRate(rate))
        } catch (e: Exception){
            TeacherRatesError
        }
    }

    fun addComment(comment: StudentComment) = execute {
        viewState = try {
            TeacherCommentsUpdated(userInteractor.addComment(comment))
        } catch (e: Exception) {
            TeacherRatesError
        }
    }
}
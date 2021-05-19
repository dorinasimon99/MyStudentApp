package hu.bme.aut.mystudentapp.ui.schedule

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import java.lang.Exception
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val coursesInteractor: CoursesInteractor,
    private val userInteractor: UserInteractor
) : RainbowCakeViewModel<ScheduleViewState>(ScheduleInitial) {
    fun loadSchedule() = execute {
        viewState = ScheduleInitial
        viewState = try {
            val courses = coursesInteractor.loadCourses()
            ScheduleLoaded(courses)
        } catch (e: Exception) {
            ScheduleError
        }
    }
}
package hu.bme.aut.mystudentapp.ui.schedule

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import java.lang.Exception
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val schedulePresenter: SchedulePresenter
) : RainbowCakeViewModel<ScheduleViewState>(ScheduleInitial) {
    fun loadSchedule() = execute {
        viewState = ScheduleInitial
        viewState = try {
            val courses = schedulePresenter.loadCourses()
            ScheduleLoaded(courses)
        } catch (e: Exception) {
            ScheduleError
        }
    }
}
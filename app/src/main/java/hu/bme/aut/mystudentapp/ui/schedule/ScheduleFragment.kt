package hu.bme.aut.mystudentapp.ui.schedule

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R

class ScheduleFragment : RainbowCakeFragment<ScheduleViewState, ScheduleViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSchedule()
    }

    override fun getViewResource() = R.layout.fragment_schedule

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: ScheduleViewState) {
        when(viewState){
            ScheduleInitial -> {
                (activity as MainActivity).supportActionBar?.title = "Timetable"
            }
            is ScheduleLoaded -> {
                if(viewState.courses != null){
                    val schedules = ArrayList<Schedule>()
                    var schedule = Schedule()
                    for(course in viewState.courses){
                        schedule.classTitle = course.name
                        var day = course.time!!.substringBefore(" ", "Cannot access day")
                        var hour = course.time!!.substringBefore(":", "-1")
                        var minute = course.time!!.substringAfter(":", "-1")
                        if(hour.toInt() != -1 && minute.toInt() != -1) schedule.startTime = Time(hour.toInt(), minute.toInt())

                    }
                } else {

                }
            }
            ScheduleError -> {}
        }.exhaustive
    }
}
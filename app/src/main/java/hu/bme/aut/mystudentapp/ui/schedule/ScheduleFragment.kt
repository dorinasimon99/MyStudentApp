package hu.bme.aut.mystudentapp.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_schedule.*

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
                    Log.i("ScheduleFragment", "courses size: ${viewState.courses.size}")
                    for(course in viewState.courses){
                        var schedule = Schedule()
                        schedule.classTitle = course.name
                        var day = course.time!!.substringBefore(" ", "Cannot access day")
                        var hour = course.time!!.substringAfter(" ").substringBefore(":", "-1")
                        var minute = course.time!!.substringAfter(":", "-1").substringBefore(" - ")
                        if(hour.toInt() != -1 && minute.toInt() != -1) schedule.startTime = Time(hour.toInt(), minute.toInt())
                        hour = course.time!!.substringAfter(" - ").substringBefore(":", "-1")
                        minute = course.time!!.substringAfterLast(":", "-1")
                        if(hour.toInt() != -1 && minute.toInt() != -1) schedule.endTime = Time(hour.toInt(), minute.toInt())
                        schedule.day = when(day){
                            "Monday" -> 0
                            "TuesDay" -> 1
                            "Wednesday" -> 2
                            "Thursday" -> 3
                            "Friday" -> 4
                            else -> {
                                Log.i("ScheduleFragment", "Invalid day")}
                        }
                        schedules.add(schedule)
                    }
                    timeTable.add(schedules)
                } else {

                }
            }
            ScheduleError -> {}
        }.exhaustive
    }
}
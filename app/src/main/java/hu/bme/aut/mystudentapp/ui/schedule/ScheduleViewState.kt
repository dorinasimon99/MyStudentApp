package hu.bme.aut.mystudentapp.ui.schedule

import hu.bme.aut.mystudentapp.data.model.Course

sealed class ScheduleViewState

object ScheduleInitial : ScheduleViewState()

data class ScheduleLoaded(val courses : List<Course>?) : ScheduleViewState()

object ScheduleError : ScheduleViewState()
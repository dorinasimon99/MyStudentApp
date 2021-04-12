package hu.bme.aut.mystudentapp.ui.schedule

import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import javax.inject.Inject

class SchedulePresenter @Inject constructor(
    private val coursesInteractor: CoursesInteractor
) {
    suspend fun loadCourses() : List<Course>?{
        return coursesInteractor.loadCourses()
    }
}
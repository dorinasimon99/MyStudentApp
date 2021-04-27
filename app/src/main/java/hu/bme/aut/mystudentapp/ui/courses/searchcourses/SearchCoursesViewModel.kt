package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.ui.courses.CoursesPresenter
import java.lang.Exception
import javax.inject.Inject

class SearchCoursesViewModel @Inject constructor(
    private val coursesPresenter: CoursesPresenter
)
: RainbowCakeViewModel<SearchCoursesViewState>(SearchCoursesInitial) {

    fun searchCourses() = execute {
        viewState = SearchCoursesInitial
        viewState = try {
            coursesPresenter.loadAllCourses()
            ListSearchCourses
        } catch (e: Exception){
            SearchCoursesError(e)
        }
    }

    fun addCourseToLocalCourses(c: Course) = execute {
        viewState = try {
            coursesPresenter.addCourseToLocal(c)
            ListCourseAdded
        } catch (e: Exception){
            SearchCoursesError(e)
        }
    }
}
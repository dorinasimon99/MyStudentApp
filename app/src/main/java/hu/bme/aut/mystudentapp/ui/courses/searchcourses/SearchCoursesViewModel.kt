package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.interactor.CoursesInteractor
import hu.bme.aut.mystudentapp.interactor.UserInteractor
import java.lang.Exception
import javax.inject.Inject

class SearchCoursesViewModel @Inject constructor(
    private val coursesInteractor: CoursesInteractor,
    private val userInteractor: UserInteractor
)
: RainbowCakeViewModel<SearchCoursesViewState>(SearchCoursesInitial) {

    fun searchCourses() = execute {
        viewState = SearchCoursesInitial
        viewState = try {
            ListSearchCourses(coursesInteractor.loadAllCourses())
        } catch (e: Exception){
            SearchCoursesError(e)
        }
    }

    fun addCourseToLocalCourses(c: Course) = execute {
        viewState = try {
            coursesInteractor.addCourseToLocal(c)
            ListCourseAdded
        } catch (e: Exception){
            SearchCoursesError(e)
        }
    }
}
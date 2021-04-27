package hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher

import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.mystudentapp.R

class CourseDetailsTeacherFragment : RainbowCakeFragment<CourseDetailsTeacherViewState, CourseDetailsTeacherViewModel>() {

    override fun getViewResource() = R.layout.fragment_course_details_teacher

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: CourseDetailsTeacherViewState) {
        when(viewState){
            CourseDetailsTeacherInitial -> {}
            CourseDetailsTeacherError -> {}
        }.exhaustive
    }

}
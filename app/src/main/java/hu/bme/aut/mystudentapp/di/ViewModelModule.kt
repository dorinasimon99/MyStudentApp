package hu.bme.aut.mystudentapp.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.mystudentapp.ui.courses.addcourse.AddCourseViewModel
import hu.bme.aut.mystudentapp.ui.courses.CoursesViewModel
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent.CourseDetailsViewModel
import hu.bme.aut.mystudentapp.ui.courses.searchcourses.SearchCoursesViewModel
import hu.bme.aut.mystudentapp.ui.main.MainScreenViewModel
import hu.bme.aut.mystudentapp.ui.schedule.ScheduleViewModel
import hu.bme.aut.mystudentapp.ui.selectrole.SelectRoleScreenViewModel
import hu.bme.aut.mystudentapp.ui.signin.SignInScreenViewModel
import hu.bme.aut.mystudentapp.ui.student.StudentScreenViewModel
import hu.bme.aut.mystudentapp.ui.teacher.TeacherViewModel
import hu.bme.aut.mystudentapp.ui.teacher.teacherrating.TeacherRatesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInScreenViewModel::class)
    abstract fun bindSignInViewModel(signInViewModel: SignInScreenViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectRoleScreenViewModel::class)
    abstract fun bindSelectRoleViewModel(selectRoleViewModel: SelectRoleScreenViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StudentScreenViewModel::class)
    abstract fun bindStudentViewModel(studentViewModel: StudentScreenViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoursesViewModel::class)
    abstract fun bindCoursesViewModel(coursesViewModel: CoursesViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCourseViewModel::class)
    abstract fun bindAddCourseViewModel(addCourseViewModel: AddCourseViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CourseDetailsViewModel::class)
    abstract fun bindCourseDetailsViewModel(courseDetailsViewModel: CourseDetailsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeacherRatesViewModel::class)
    abstract fun bindTeacherRatesViewModel(teacherRatesViewModel: TeacherRatesViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchCoursesViewModel::class)
    abstract fun bindSearchCoursesViewModel(searchCoursesViewModel: SearchCoursesViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(scheduleViewModel: ScheduleViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainScreenViewModel(mainScreenViewModel: MainScreenViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeacherViewModel::class)
    abstract fun bindTeacherViewModel(teacherViewModel: TeacherViewModel) : ViewModel
}
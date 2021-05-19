package hu.bme.aut.mystudentapp.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.mystudentapp.ui.courses.addcourse.AddCourseViewModel
import hu.bme.aut.mystudentapp.ui.courses.CoursesViewModel
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent.CourseDetailsStudentViewModel
import hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher.CourseDetailsTeacherViewModel
import hu.bme.aut.mystudentapp.ui.courses.searchcourses.SearchCoursesViewModel
import hu.bme.aut.mystudentapp.ui.main.MainViewModel
import hu.bme.aut.mystudentapp.ui.schedule.ScheduleViewModel
import hu.bme.aut.mystudentapp.ui.selectrole.SelectRoleViewModel
import hu.bme.aut.mystudentapp.ui.signin.SignInViewModel
import hu.bme.aut.mystudentapp.ui.student.StudentViewModel
import hu.bme.aut.mystudentapp.ui.teacher.TeacherViewModel
import hu.bme.aut.mystudentapp.ui.teacher.teacherrating.TeacherRatesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(signInViewModel: SignInViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectRoleViewModel::class)
    abstract fun bindSelectRoleViewModel(selectRoleViewModel: SelectRoleViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StudentViewModel::class)
    abstract fun bindStudentViewModel(studentViewModel: StudentViewModel) : ViewModel

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
    @ViewModelKey(CourseDetailsStudentViewModel::class)
    abstract fun bindCourseDetailsViewModel(courseDetailsStudentViewModel: CourseDetailsStudentViewModel) : ViewModel

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
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainScreenViewModel(mainViewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeacherViewModel::class)
    abstract fun bindTeacherViewModel(teacherViewModel: TeacherViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CourseDetailsTeacherViewModel::class)
    abstract fun bindCourseDetailsTeacherTeacherViewModel(courseDetailsTeacherViewModel: CourseDetailsTeacherViewModel) : ViewModel
}
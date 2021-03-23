package hu.bme.aut.mystudentapp.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.mystudentapp.ui.courses.AddCourseViewModel
import hu.bme.aut.mystudentapp.ui.courses.CoursesViewModel
import hu.bme.aut.mystudentapp.ui.main.MainScreenViewModel
import hu.bme.aut.mystudentapp.ui.selectrole.SelectRoleScreenViewModel
import hu.bme.aut.mystudentapp.ui.signin.SignInScreenViewModel
import hu.bme.aut.mystudentapp.ui.student.StudentScreenViewModel

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
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainScreenViewModel(mainScreenViewModel: MainScreenViewModel) : ViewModel
}
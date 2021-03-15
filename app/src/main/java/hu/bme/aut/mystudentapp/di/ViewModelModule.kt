package hu.bme.aut.mystudentapp.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hu.bme.aut.mystudentapp.viewmodel.MainScreenViewModel
import hu.bme.aut.mystudentapp.viewmodel.SelectRoleScreenViewModel
import hu.bme.aut.mystudentapp.viewmodel.SignInScreenViewModel
import hu.bme.aut.mystudentapp.viewmodel.StudentScreenViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainScreenViewModel) : ViewModel

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
}
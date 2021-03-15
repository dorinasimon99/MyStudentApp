package hu.bme.aut.mystudentapp.view.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.UserDataSource
import hu.bme.aut.mystudentapp.viewmodel.MainScreenViewModel
import hu.bme.aut.mystudentapp.viewmodel.StudentScreenViewModel
import hu.bme.aut.mystudentapp.viewstate.*
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_student_welcome.*
import kotlinx.android.synthetic.main.fragment_student_welcome.view.*

class StudentWelcomeFragment : RainbowCakeFragment<StudentScreenViewState, StudentScreenViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStudent()
        //TODO: clickListeners
    }

    override fun getViewResource() = R.layout.fragment_student_welcome

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: StudentScreenViewState) {
        when(viewState){
            StudentBegin -> {}
            is StudentInitial -> {
                if(viewState.user?.name != null){
                    tvWelcomeStudent.text = viewState.user.name.toString()
                }
            }
            StudentError -> {}
        }
    }
}
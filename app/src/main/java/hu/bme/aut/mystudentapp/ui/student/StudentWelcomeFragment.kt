package hu.bme.aut.mystudentapp.ui.student

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_student_welcome.*

class StudentWelcomeFragment : RainbowCakeFragment<StudentScreenViewState, StudentScreenViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStudent()
        btnClasses.setOnClickListener {
            findNavController().navigate(R.id.action_studentMainFragment_to_coursesFragment)
        }

        btnSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_studentMainFragment_to_scheduleFragment)
        }
    }

    override fun getViewResource() = R.layout.fragment_student_welcome

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: StudentScreenViewState) {
        when(viewState){
            StudentBegin -> {

            }
            is StudentInitial -> {
                if(viewState.user?.name != null){
                    (activity as MainActivity).supportActionBar?.title = "Welcome ${viewState.user.name}!"
                } else (activity as MainActivity).supportActionBar?.title = "Welcome!"
            }
            StudentError -> {
                Toast.makeText(context, "Student fragment error", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }
}
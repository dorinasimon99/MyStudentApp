package hu.bme.aut.mystudentapp.ui.teacher

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_teacher_welcome.*

class TeacherWelcomeFragment : RainbowCakeFragment<TeacherViewState, TeacherViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTeacher()
        btnClasses.setOnClickListener {
            findNavController().navigate(R.id.action_teacherMainFragment_to_coursesFragment)
        }

        btnSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_teacherMainFragment_to_scheduleFragment)
        }
    }

    override fun getViewResource() = R.layout.fragment_welcome

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: TeacherViewState) {
        when(viewState){
            TeacherBegin -> {

            }
            is TeacherInitial -> {
                if(viewState.user?.name != null){
                    (activity as MainActivity).supportActionBar?.title = "Welcome ${viewState.user.name}!"
                } else (activity as MainActivity).supportActionBar?.title = "Welcome!"
            }
            TeacherError -> {
                Toast.makeText(context, "Teacher fragment error", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }
}
package hu.bme.aut.mystudentapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.view.student.StudentWelcomeFragment
import hu.bme.aut.mystudentapp.view.teacher.TeacherWelcomeFragment
import hu.bme.aut.mystudentapp.viewmodel.MainScreenViewModel
import hu.bme.aut.mystudentapp.viewstate.*
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_main.*


@Suppress("IMPLICIT_CAST_TO_ANY")
class MainScreenFragment : RainbowCakeFragment<MainScreenViewState, MainScreenViewModel>() {

    //private val navController = findNavController()

    override fun getViewResource() = R.layout.fragment_main

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUserData(requireActivity() as AppCompatActivity)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleWebUI(requestCode, resultCode, data)
    }*/


    override fun render(viewState: MainScreenViewState) {
        when(viewState){
            Initial -> {
                mainProgressBar.visibility = View.INVISIBLE
            }//TODO: hatterkep
            Loading -> {
                mainProgressBar.visibility = View.VISIBLE
            }
            SignIn -> {
                /*val signInFragment = SignInFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, signInFragment)
                    .addToBackStack(null)
                    .commit()*/
                findNavController().navigate(R.id.action_mainScreenFragment_to_signInFragment)
            }
            is DataReady -> {
                when (viewState.user?.role) {
                    null -> {
                        /*val selectRoleFragment = SelectRoleFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, selectRoleFragment)
                            .addToBackStack(null)
                            .commit()*/
                        findNavController().navigate(R.id.action_mainScreenFragment_to_selectRoleFragment)
                    }
                    Role.STUDENT.toString() -> {
                        /*val studentFragment = StudentWelcomeFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, studentFragment)
                            .addToBackStack(null)
                            .commit()*/
                        findNavController().navigate(R.id.action_mainScreenFragment_to_studentMainFragment)
                    }
                    Role.TEACHER.toString() -> {
                        /*val teacherFragment = TeacherWelcomeFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, teacherFragment)
                            .addToBackStack(null)
                            .commit()*/
                        findNavController().navigate(R.id.action_mainScreenFragment_to_teacherMainFragment)
                    }
                    else -> throw Exception("User doesn't have role!")
                }
            }
            DataError -> {
                Toast.makeText(context, "Data loading error!", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }
}
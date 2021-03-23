package hu.bme.aut.mystudentapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.model.Role
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainScreenFragment : RainbowCakeFragment<MainScreenViewState, MainScreenViewModel>() {

    //private val navController = findNavController()

    override fun getViewResource() = R.layout.fragment_main

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUserData(requireActivity() as AppCompatActivity)
    }

    override fun render(viewState: MainScreenViewState) {
        when(viewState){
            Initial -> {
                mainProgressBar.visibility = View.INVISIBLE
            }//TODO: hatterkep
            Loading -> {
                mainProgressBar.visibility = View.VISIBLE
            }
            SignIn -> {
                findNavController().navigate(R.id.signInFragment)
            }
            is DataReady -> {
                when (viewState.user?.role) {
                    null -> {
                        findNavController().navigate(R.id.selectRoleFragment)
                    }
                    Role.STUDENT.toString() -> {
                        findNavController().navigate(R.id.studentMainFragment)
                    }
                    Role.TEACHER.toString() -> {
                        findNavController().navigate(R.id.teacherMainFragment)
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
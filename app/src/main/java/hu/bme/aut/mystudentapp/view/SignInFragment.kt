package hu.bme.aut.mystudentapp.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.viewmodel.SignInScreenViewModel
import hu.bme.aut.mystudentapp.viewstate.SignInError
import hu.bme.aut.mystudentapp.viewstate.SignInInitial
import hu.bme.aut.mystudentapp.viewstate.SignInLoading
import hu.bme.aut.mystudentapp.viewstate.SignInScreenViewState
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_login.*

class SignInFragment : RainbowCakeFragment<SignInScreenViewState, SignInScreenViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            viewModel.signIn(requireActivity())
        }
    }

    override fun getViewResource() = R.layout.fragment_login

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: SignInScreenViewState) {
        when(viewState){
            SignInInitial -> {
                signInProgressBar.visibility = View.INVISIBLE
            }
            is SignInLoading -> {
                signInProgressBar.visibility = View.VISIBLE
                btnLogin.visibility = View.INVISIBLE
                when(viewState.user?.role){
                    null -> {
                        findNavController().navigate(R.id.action_signInFragment_to_selectRoleFragment)
                    }
                    Role.STUDENT.toString() -> {
                        findNavController().navigate(R.id.studentMainFragment)
                    }
                    Role.TEACHER.toString() -> {
                        findNavController().navigate(R.id.teacherMainFragment)
                    }
                    else -> {}
                }
            }
            SignInError -> {}
        }.exhaustive
    }
}
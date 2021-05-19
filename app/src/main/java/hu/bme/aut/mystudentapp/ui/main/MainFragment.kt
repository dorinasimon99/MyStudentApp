package hu.bme.aut.mystudentapp.ui.main

import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.Role
import hu.mystudentapp.R

class MainFragment : RainbowCakeFragment<MainScreenViewState, MainViewModel>(){

    override fun onStart() {
        super.onStart()
        viewModel.getLocalUser(viewLifecycleOwner)
    }

    override fun getViewResource() = R.layout.fragment_main

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: MainScreenViewState) {

        when(viewState){
            MainScreenInitial -> {}
            is MainScreenLocalUser -> {
                localUser = viewState.user
            }
            is MainScreenUserData -> {
                when(viewState.user){
                    null -> { findNavController().navigate(R.id.selectRoleFragment) }
                    else -> {
                        when(viewState.user.role){
                            Role.STUDENT.toString() -> {
                                findNavController().navigate(R.id.studentMainFragment)
                            }
                            Role.TEACHER.toString() -> {
                                findNavController().navigate(R.id.teacherMainFragment)
                            }
                            Role.NO_ROLE.toString() -> {}
                            else -> {}
                        }.exhaustive
                    }
                }
            }
            MainScreenSignedOut -> {
                findNavController().navigate(R.id.signInFragment)
            }
            is MainScreenError -> {
                Toast.makeText(context, viewState.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

    companion object{
        var localUser : LocalUserData? = null
    }
}
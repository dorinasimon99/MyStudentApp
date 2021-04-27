package hu.bme.aut.mystudentapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import hu.mystudentapp.R

/*class MainScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        return view
    }
}*/

class MainScreenFragment : RainbowCakeFragment<MainScreenViewState, MainScreenViewModel>(){

    override fun onStart() {
        super.onStart()
        viewModel.getLocalUser(viewLifecycleOwner)
    }

    override fun getViewResource() = R.layout.fragment_main

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: MainScreenViewState) {

        when(viewState){
            MainScreenInitial -> {
                Log.i("MainScreenFragment", "MainScreenInitial")
            }
            is MainScreenLocalUser -> {
                localUser = viewState.user
                Log.i("MainScreenFragment", "MainScreenLocalUser: $localUser")
            }
            is MainScreenError -> {
                Log.e("MainScreenFragment", "MainScreenError: ${viewState.error}")
                //Toast.makeText(context, viewState.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

    companion object{
        var localUser : LocalUserData? = null
    }
}
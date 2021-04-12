package hu.bme.aut.mystudentapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.data.model.Role
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
            }
            is SignInLoading -> {
                btnLogin.visibility = View.INVISIBLE
                when(viewState.user?.role){
                    null -> {
                        findNavController().navigate(R.id.action_signInFragment_to_selectRoleFragment)
                    }
                    Role.STUDENT.toString() -> {
                        findNavController().navigate(R.id.action_signInFragment_to_studentMainFragment)
                    }
                    Role.TEACHER.toString() -> {
                        findNavController().navigate(R.id.action_signInFragment_to_teacherMainFragment)
                    }
                    else -> {}
                }
            }
            SignInError -> {}
        }.exhaustive
    }

    // pass the data from web redirect to Amplify libs
    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SignInFragment", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }
}
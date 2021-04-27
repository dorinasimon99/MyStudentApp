package hu.bme.aut.mystudentapp.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Role
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : RainbowCakeFragment<SignInScreenViewState, SignInScreenViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            viewModel.signIn(etUsername.text.toString(), etPassword.text.toString())
        }

        btnSignUp.setOnClickListener {
            btnLogin.visibility = View.GONE
            tvOR.visibility = View.GONE
            btnSignUp.visibility = View.GONE
            textInputEmail.visibility = View.VISIBLE
            textInputConfirmCode.visibility = View.VISIBLE
            btnConfirm.visibility = View.VISIBLE
            btnSignUp2.visibility = View.VISIBLE
        }

        btnSignUp2.setOnClickListener {
            viewModel.signUp(etUsername.text.toString(), etPassword.text.toString(), etEmail.text.toString())
        }

        btnConfirm.setOnClickListener {
            viewModel.confirmSignUp(etUsername.text.toString(), etConfirmationCode.text.toString(), etPassword.text.toString())
        }

    }

    override fun getViewResource() = R.layout.fragment_sign_in

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: SignInScreenViewState) {
        when(viewState){
            SignInInitial -> {
                Log.i("SignInFragment", "SignInInitial")//Toast.makeText(context, "SignIn Initial", Toast.LENGTH_SHORT).show()
            }
            SignedIn -> {
                Log.i("SignInFragment", "SignedIn")//Toast.makeText(context, "Signed In", Toast.LENGTH_SHORT).show()

            }
            SignUp -> {}
            ConfirmedSignUp -> {
                findNavController().navigate(R.id.action_signInFragment_to_selectRoleFragment)
            }
            /*is SignInLoading -> {
                Log.i("SignInFragment", "SignInLoading")
                when(viewState.user){
                    null -> {
                        findNavController().navigate(R.id.action_signInFragment_to_selectRoleFragment)
                    }
                    else -> {}
                }
            }*/
            is SignInError -> {
                Log.e("SignInFragment", viewState.e.toString())//Toast.makeText(context, viewState.e.toString(), Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

    fun handleWebUISignInResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("SignInFragment", "received requestCode : $requestCode and resultCode : $resultCode")
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }
}
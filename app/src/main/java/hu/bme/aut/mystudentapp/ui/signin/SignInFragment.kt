package hu.bme.aut.mystudentapp.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.model.Role
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_sign_in.*

@Suppress("IMPLICIT_CAST_TO_ANY")
class SignInFragment : RainbowCakeFragment<SignInScreenViewState, SignInViewModel>() {

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
            btnSendConfirm.visibility = View.VISIBLE
        }

        btnSendConfirm.setOnClickListener {
            if(etEmail.text.isNullOrEmpty()){
                etEmail.requestFocus()
                etEmail.error = "Required!"
            }
            if(etUsername.text.isNullOrEmpty()){
                etUsername.requestFocus()
                etUsername.error = "Required!"
            }
            if(etPassword.text.isNullOrEmpty()){
                etPassword.requestFocus()
                etPassword.error = "Required!"
            }
            else if(etPassword.text!!.length < 8){
                etPassword.requestFocus()
                etPassword.error = "Password is too short!"
            }
            viewModel.signUp(etUsername.text.toString(), etPassword.text.toString(), etEmail.text.toString())
        }

        btnConfirm.setOnClickListener {
            if(etConfirmationCode.text.isNullOrEmpty()){
                etConfirmationCode.requestFocus()
                etConfirmationCode.error = "Required!"
            }
            viewModel.confirmSignUp(etUsername.text.toString(), etConfirmationCode.text.toString(), etPassword.text.toString())
        }

    }

    override fun getViewResource() = R.layout.fragment_sign_in

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: SignInScreenViewState) {
        when(viewState){
            SignInInitial -> {
                Log.i("SignInFragment", "SignInInitial")
            }
            SignedIn -> {
                Toast.makeText(context, "Signed In", Toast.LENGTH_SHORT).show()
            }
            is SignInUserData -> {
                when(viewState.user){
                    null -> {
                        val bundle = bundleOf("username" to etUsername.text.toString())
                        findNavController().navigate(R.id.selectRoleFragment, bundle)
                    }
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
            ConfirmedSignUp -> {
                findNavController().navigate(R.id.action_signInFragment_to_selectRoleFragment)
            }
            is SignInError -> {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Error")
                builder.setMessage(viewState.e.message.toString())
                builder.setPositiveButton("Ok"){dialogInterface, which ->}
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }.exhaustive
    }
}
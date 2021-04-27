package hu.bme.aut.mystudentapp.ui.selectrole

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_select_role.*
import java.util.*

class SelectRoleFragment : RainbowCakeDialogFragment<SelectRoleScreenViewState, SelectRoleScreenViewModel>() {

    private lateinit var role : Role

    override fun getViewResource() = R.layout.fragment_select_role

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOk.setOnClickListener {
            var fullname = ""
            if (etFirstName.text!!.isEmpty()) {
                etFirstName.requestFocus()
                etFirstName.error = "This field is required!"
            } else fullname += etFirstName.text.toString() + " "
            if (etLastName.text!!.isEmpty()) {
                etLastName.requestFocus()
                etLastName.error = "This field is required!"
            } else fullname += etLastName.text.toString()
            if (etUsername.text!!.isEmpty()) {
                etUsername.requestFocus()
                etUsername.error = "This field is required!"
            }
            when {
                rbStudent.isChecked -> {
                    val user = User(UUID.randomUUID().toString(), fullname,
                        Role.STUDENT.toString(), etUsername.text.toString())
                    role = Role.STUDENT
                    viewModel.selectRole(user)
                }
                rbTeacher.isChecked -> {
                    val user = User(UUID.randomUUID().toString(), fullname,
                        Role.TEACHER.toString(), etUsername.text.toString())
                    role = Role.TEACHER
                    viewModel.selectRole(user)
                }
                else -> {
                    textView.requestFocus()
                    textView.error = "Please choose a role"
                }
            }
        }
    }

    override fun render(viewState: SelectRoleScreenViewState) {
        when(viewState){
            SelectRoleInitial -> {
                (activity as MainActivity).supportActionBar?.title = "Select role"
            }
            SelectRole -> {
                Toast.makeText(context, "Role: $role", Toast.LENGTH_SHORT).show()
                when(role){
                    Role.STUDENT -> {
                        findNavController().navigate(R.id.action_selectRoleFragment_to_studentMainFragment)
                    }
                    Role.TEACHER -> {
                        findNavController().navigate(R.id.action_selectRoleFragment_to_teacherMainFragment)
                    }
                }.exhaustive
            }
            SelectRoleError -> {
                Toast.makeText(context, "Role select failed", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }
}
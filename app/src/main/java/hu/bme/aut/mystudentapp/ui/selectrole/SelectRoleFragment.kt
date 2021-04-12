package hu.bme.aut.mystudentapp.ui.selectrole

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
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
            if(rbStudent.isChecked){
                val username = etUserName.text.toString()
                val user = User(UUID.randomUUID().toString(), username, Role.STUDENT.toString())
                role = Role.STUDENT
                viewModel.selectRole(user)
            }
            else if(rbTeacher.isChecked){
                val username = etUserName.text.toString()
                val user = User(UUID.randomUUID().toString(), username, Role.TEACHER.toString())
                role = Role.TEACHER
                viewModel.selectRole(user)
            }
            else {
                textView.requestFocus()
                textView.error = "Please choose a role"
            }
        }
    }

    override fun render(viewState: SelectRoleScreenViewState) {
        when(viewState){
            SelectRoleInitial -> {
                (activity as MainActivity).supportActionBar?.title = "Select role"
            }
            SelectRole -> {
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

            }
        }.exhaustive
    }
}
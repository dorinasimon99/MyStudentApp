package hu.bme.aut.mystudentapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mystudentapp.backend.Backend
import hu.bme.aut.mystudentapp.backend.Role
import hu.bme.aut.mystudentapp.backend.Data
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_dialog_select_role.*
import kotlinx.android.synthetic.main.fragment_dialog_select_role.view.*
import java.util.*

class SelectRoleDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_select_role, container, false)

        view.btnOk.setOnClickListener {
            if(rbStudent.isChecked){
                val username = etUserName.text.toString()
                val user = Data.User(UUID.randomUUID().toString(), username, Role.STUDENT.toString(), null)
                Data.setUserData(user)
            }
            else if(rbTeacher.isChecked){
                val username = etUserName.text.toString()
                val user = Data.User(UUID.randomUUID().toString(), username, Role.TEACHER.toString(), null)
                Data.setUserData(user)
            }
            dismiss()
        }

        return view
    }


}
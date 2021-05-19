package hu.bme.aut.mystudentapp.ui.add_dialogs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.dialog_add_comment.*
import kotlinx.android.synthetic.main.dialog_add_comment.view.*
import kotlinx.android.synthetic.main.fragment_teacher_rates.*
import kotlinx.android.synthetic.main.single_comment.*

class AddCommentDialog(val coursesList: ArrayList<String>) : DialogFragment(), AdapterView.OnItemSelectedListener {

    var selectedCourse = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_add_comment, container, false)

        view.tbtnCommenterName.textOn = UserDataBackend.currentUser.name

        view.btnCommentOk.setOnClickListener {
            sendResult(0)
            this.dismiss()
        }

        view.btnCommentCancel.setOnClickListener {
            this.dismiss()
        }

        val courseSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, coursesList)
        courseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinnerCommentCourses.adapter = courseSpinnerAdapter

        view.spinnerCommentCourses.onItemSelectedListener = this

        return view
    }

    private fun sendResult(REQUEST_CODE: Int){
        val intent = Intent()
        val commenterName = if(tbtnCommenterName.isChecked){
            UserDataBackend.currentUser.name
        } else {
            "Anonymous"
        }
        intent.putExtra(COMMENT_TEXT, etCommentText.text.toString())
        intent.putExtra(COMMENT_NAME, commenterName)
        intent.putExtra(COURSE_NAME, selectedCourse)
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }

    companion object{
        val COMMENT_TEXT = "Comment text"
        val COMMENT_NAME = "Commenter name"
        val COURSE_NAME = "Course name"
        var REQUEST_CODE = 0
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCourse = parent!!.getItemAtPosition(position).toString()
        Log.i("AddCommentDialog", "SelectedCourse: $selectedCourse")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
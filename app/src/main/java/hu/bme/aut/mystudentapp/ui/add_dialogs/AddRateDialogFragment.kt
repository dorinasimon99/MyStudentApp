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
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.dialog_add_rate.*
import kotlinx.android.synthetic.main.dialog_add_rate.view.*

class AddRateDialogFragment(val coursesList: ArrayList<String>) : DialogFragment(), AdapterView.OnItemSelectedListener {

    var selectedCourse = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_add_rate, container, false)

        view.btnRatingOk.setOnClickListener {
            sendResult(1)
            this.dismiss()
        }

        view.btnRatingCancel.setOnClickListener {
            this.dismiss()
        }

        val courseSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, coursesList)
        courseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinnerCourses.adapter = courseSpinnerAdapter

        view.spinnerCourses.onItemSelectedListener = this

        return view
    }

    private fun sendResult(REQUEST_CODE: Int){
        val intent = Intent()
        intent.putExtra(RATING_VALUE, ratingBar.rating.toString())
        intent.putExtra(RATING_COURSE, selectedCourse)
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }

    companion object{
        const val RATING_VALUE = "Rating value"
        const val RATING_COURSE = "Course name"
        var REQUEST_CODE = 1
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCourse = parent!!.getItemAtPosition(position).toString()
        Log.i("AddCommentDialog", "SelectedCourse: $selectedCourse")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
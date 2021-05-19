package hu.bme.aut.mystudentapp.ui.courses.addcourse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend.currentUser
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.timepickerdialog.TimePickerRangeDialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_add_course.*
import java.util.*
import kotlin.collections.ArrayList

class AddCourseFragment : RainbowCakeFragment<AddCourseViewState, AddCourseViewModel>(){

    interface NewCourseFragmentListener {
        fun onCourseCreated(newCourse: Course)
    }

    private lateinit var listener : NewCourseFragmentListener

    private val pickerValues = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

    override fun getViewResource() = R.layout.fragment_add_course

    override fun provideViewModel() = getViewModelFromFactory()

    var start = ""
    var end = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dayPicker.minValue = 0
        dayPicker.maxValue = pickerValues.size - 1
        dayPicker.displayedValues = pickerValues
        dayPicker.wrapSelectorWheel = true

        numpickerCredit.minValue = 2
        numpickerCredit.maxValue = 15
        numpickerCredit.wrapSelectorWheel = true

        listener = context as NewCourseFragmentListener

        btnSelectCourse.setOnClickListener {
            findNavController().navigate(R.id.searchCoursesFragment)
        }

        btnSelectTime.setOnClickListener {
            val timePickerDialogFragment = TimePickerRangeDialogFragment()
            timePickerDialogFragment.setTargetFragment(this, 0)
            timePickerDialogFragment.show(requireFragmentManager(), "TimePickerDialogFragment")
        }

        btnCancel.setOnClickListener {
            findNavController().navigate(R.id.coursesFragment)
        }

        btnOk.setOnClickListener {
            if(etNewCourseCode.text.isEmpty()){
                etNewCourseCode.requestFocus()
                etNewCourseCode.error = "Add a course code!"
            }
            if(etNewCourseName.text.isEmpty()){
                etNewCourseName.requestFocus()
                etNewCourseName.error = "Add a course name!"
            }
            else {
                val userList = ArrayList<User>()
                userList.add(User.from(currentUser))
                val teachersList = ArrayList<String>()
                val course = Course(
                    UUID.randomUUID().toString(),
                    etNewCourseCode.text.toString(),
                    etNewCourseName.text.toString(),
                    numpickerCredit.value,
                    time = pickerValues[dayPicker.value] + " " + start + " - " + end,
                    userList,
                    ArrayList(),
                    ArrayList(),
                    teachersList,
                    ArrayList()
                )

                if(UserDataBackend.courses().contains(course)){
                    Toast.makeText(
                        context,
                        "This course is already in the courses list, add this course from there",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else {
                    listener.onCourseCreated(course)
                    viewModel.addCourse(course)
                }

            }
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?){
        if(requestCode == TimePickerRangeDialogFragment.REQUEST_CODE){
            start = data!!.getStringExtra(TimePickerRangeDialogFragment.START_TIME_BUNDLE_EXTRA).toString()
            end = data.getStringExtra(TimePickerRangeDialogFragment.END_TIME_BUNDLE_EXTRA).toString()
            val text = "$start - $end"
            tvCourseTime.text = text
        }
    }

    override fun render(viewState: AddCourseViewState) {
        when(viewState){
            AddCourse -> {
                (activity as MainActivity).supportActionBar?.title = "Add course"
            }
            CourseAdded -> {
                findNavController().navigate(R.id.coursesFragment)
            }
            AddCourseError -> {
                Toast.makeText(context, "Course add failed", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

}
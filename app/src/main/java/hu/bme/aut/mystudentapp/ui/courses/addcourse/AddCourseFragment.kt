package hu.bme.aut.mystudentapp.ui.courses.addcourse

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend.currentUser
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_add_course.*
import java.util.*
import kotlin.collections.ArrayList

class AddCourseFragment : RainbowCakeFragment<AddCourseViewState, AddCourseViewModel>() {

    interface NewCourseFragmentListener {
        fun onCourseCreated(newCourse: Course)
    }

    private lateinit var listener : NewCourseFragmentListener

    private val pickerValues = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

    override fun getViewResource() = R.layout.fragment_add_course

    override fun provideViewModel() = getViewModelFromFactory()

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

        var startHour = ""
        var startMinute = ""
        var endHour = ""
        var endMinute = ""

        btnSelectCourse.setOnClickListener {
            findNavController().navigate(R.id.action_addCourseFragment_to_searchCoursesFragment)
        }

        btnSelectTime.setOnClickListener {
            //TODO: saját TimeRangePickerDialogFragment
        }

        btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addCourseFragment_to_coursesFragment)
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
                if(currentUser.role == Role.TEACHER.toString()) teachersList.add(currentUser.name)
                UserDataBackend.hasUserData.value?.let { it1 -> userList.add(it1) }
                val course = Course(
                    UUID.randomUUID().toString(),
                    etNewCourseCode.text.toString(),
                    etNewCourseName.text.toString(),
                    numpickerCredit.value,
                    time = pickerValues[dayPicker.value] + " " + startHour+":"+startMinute,
                    userList,
                    ArrayList(),
                    ArrayList(),
                    teachersList,
                    ArrayList()
                )

                if(UserDataBackend.courses().value?.contains(course) == true){
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
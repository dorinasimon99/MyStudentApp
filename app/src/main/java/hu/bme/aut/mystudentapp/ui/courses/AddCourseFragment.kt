package hu.bme.aut.mystudentapp.ui.courses

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import com.amplifyframework.core.Amplify
import com.google.android.material.timepicker.TimeFormat
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Card
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.Quiz
import hu.bme.aut.mystudentapp.data.model.User
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_add_course.*
import kotlinx.android.synthetic.main.single_class.*
import okhttp3.internal.format
import java.util.*
import kotlin.collections.ArrayList

class AddCourseFragment : RainbowCakeFragment<AddCourseViewState, AddCourseViewModel>() {

    private val pickerValues = arrayOf("Mon", "Tues", "Wed", "Thurs", "Fri")

    override fun getViewResource() = R.layout.fragment_add_course

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        dayPicker.minValue = 0
        dayPicker.maxValue = pickerValues.size - 1
        dayPicker.displayedValues = pickerValues
        dayPicker.wrapSelectorWheel = true

        numpickerCredit.minValue = 0
        numpickerCredit.maxValue = 15
        numpickerCredit.wrapSelectorWheel = true

        btnSelectCourse.setOnClickListener {
            //TODO: list all courses from database
        }

        numpickerCredit.minValue = 2

        btnCancel.setOnClickListener {
            this.onDestroy()
            findNavController().navigate(R.id.coursesFragment)
        }

        btnOk.setOnClickListener {
            //TODO: add course to CourseDataBackend
            val userList = ArrayList<User>()
            UserDataBackend.hasUserData.value?.let { it1 -> userList.add(it1) }
            val course = Course(
                UUID.randomUUID().toString(),
                etNewCourseCode.text.toString(),
                etNewCourseName.text.toString(),
                numpickerCredit.value,
                time = pickerValues[dayPicker.value] + " " + tpCourseTime.hour + ":" + tpCourseTime.minute,
                userList,
                ArrayList(),
                ArrayList()
            )

            viewModel.addCourse(course)
        }
    }

    override fun render(viewState: AddCourseViewState) {
        when(viewState){
            AddCourse -> {
            }
            CourseAdded -> {
                findNavController().navigate(R.id.coursesFragment)
            }
            AddCourseError -> {
                Toast.makeText(context, "Course add failed", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
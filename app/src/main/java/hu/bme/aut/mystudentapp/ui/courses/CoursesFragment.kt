package hu.bme.aut.mystudentapp.ui.courses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.LocalDatabase
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.courses.addcourse.AddCourseFragment
import hu.bme.aut.mystudentapp.ui.courses.searchcourses.SearchCoursesFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_courses.*
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.single_class.*

class CoursesFragment : RainbowCakeFragment<CoursesViewState, CoursesViewModel>(), CoursesListAdapter.CourseItemClickListener,
AddCourseFragment.NewCourseFragmentListener{

    private lateinit var courseAdapter : CoursesListAdapter

    override fun provideViewModel() = getViewModelFromFactory()

    override fun getViewResource() = R.layout.fragment_courses

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCourses()

        courseAdapter = CoursesListAdapter(this, ArrayList())
        courses_list.adapter = courseAdapter

        fabAddCourse.setOnClickListener {
            findNavController().navigate(R.id.addCourseFragment)
        }
    }

    override fun render(viewState: CoursesViewState) {
        when(viewState){
            Initial -> {
                (activity as MainActivity).supportActionBar?.title = "My courses"
            }
            is CoursesLoading -> {
                courseAdapter.submitList(null)
                courseAdapter.submitList(viewState.courses)
                if(viewState.courses != null){
                    viewModel.saveToLocal(viewState.courses)
                } else {}
            }
            CourseLoadingError -> {
                Toast.makeText(context, "Error in loading courses", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

    override fun onCourseClicked(item: Course) {
        val bundle = bundleOf("selectedCourseName" to item.name, "selectedCourseCode" to item.courseCode)
        if(UserDataBackend.currentUser.role == Role.STUDENT.toString()){
            findNavController().navigate(R.id.courseDetailsStudentFragment, bundle)
        } else if(UserDataBackend.currentUser.role == Role.TEACHER.toString()){
            findNavController().navigate(R.id.courseDetailsTeacherFragment, bundle)
        }
    }

    override fun onCourseCreated(newCourse: Course) {
        viewModel.loadCourses()
    }
}
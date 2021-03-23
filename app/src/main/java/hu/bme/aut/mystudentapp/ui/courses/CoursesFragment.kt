package hu.bme.aut.mystudentapp.ui.courses

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Course
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_courses.*
import kotlinx.android.synthetic.main.fragment_courses.*

class CoursesFragment : RainbowCakeFragment<CoursesViewState, CoursesViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()

    override fun getViewResource() = R.layout.fragment_courses

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(courses_list)

        viewModel.loadCourses()

        fabAddCourse.setOnClickListener {
            findNavController().navigate(R.id.addCourseFragment)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        UserDataBackend.courses().observe(requireActivity(), Observer<MutableList<Course>> { courses ->
            recyclerView.adapter = CourseRecyclerViewAdapter(courses)
        })
    }

    override fun render(viewState: CoursesViewState) {
        when(viewState){
            Initial -> {}
            CoursesLoading -> {

            }
            CourseLoadingError -> {}
        }.exhaustive
    }
}
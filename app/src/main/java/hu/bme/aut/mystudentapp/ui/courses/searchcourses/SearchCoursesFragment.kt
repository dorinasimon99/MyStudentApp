package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.courses.CourseRecyclerViewAdapter
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_courses.*
import kotlinx.android.synthetic.main.fragment_search_courses.*

class SearchCoursesFragment : RainbowCakeFragment<SearchCoursesViewState, SearchCoursesViewModel>(), CourseRecyclerViewAdapter.CourseItemClickListener {

    private lateinit var adapter : CourseRecyclerViewAdapter

    override fun getViewResource() = R.layout.fragment_search_courses

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchCourses()

        setupRecyclerView(courses_list)

        searchViewCourse.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        UserDataBackend.courses().observe(requireActivity(), { courses ->
            adapter = CourseRecyclerViewAdapter(courses, this)
            recyclerView.adapter = adapter
            adapter.update(courses)
        })
    }

    override fun render(viewState: SearchCoursesViewState) {
        when(viewState){
            SearchCoursesInitial -> {
                (activity as MainActivity).supportActionBar?.title = "All courses"
            }
            ListSearchCourses -> {}
            SearchCoursesError -> {
                Toast.makeText(context, "Error in loading all courses", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

    override fun onCourseClicked(item: Course) {
        //TODO: dialogfragment, hogy biztos-e
        viewModel.addCourseToLocalCourses(item)
        findNavController().navigate(R.id.action_searchCoursesFragment_to_coursesFragment)
    }
}
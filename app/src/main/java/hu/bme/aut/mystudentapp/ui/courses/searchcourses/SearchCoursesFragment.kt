package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.Role
import hu.bme.aut.mystudentapp.data.model.User
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.courses.CourseRecyclerViewAdapter
import hu.bme.aut.mystudentapp.ui.courses.addcourse.AddCourseFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_courses.*
import kotlinx.android.synthetic.main.fragment_search_courses.*

class SearchCoursesFragment : RainbowCakeFragment<SearchCoursesViewState, SearchCoursesViewModel>(), CourseRecyclerViewAdapter.CourseItemClickListener {

    private var adapter : CourseRecyclerViewAdapter? = null

    override fun getViewResource() = R.layout.fragment_search_courses

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchCourses()

        setupRecyclerView(courses_list)

        searchViewCourse.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        UserDataBackend.courses().observe(requireActivity(), { courses ->
            adapter  = CourseRecyclerViewAdapter(courses, this)
            recyclerView.adapter = adapter
        })
    }

    override fun render(viewState: SearchCoursesViewState) {
        when(viewState){
            SearchCoursesInitial -> {
                (activity as MainActivity).supportActionBar?.title = "All courses"
            }
            ListSearchCourses -> {}
            ListCourseAdded -> {
                findNavController().navigate(R.id.coursesFragment)
            }
            is SearchCoursesError -> {
                Log.e("SearchCoursesFragment", viewState.e.toString())
            }
        }.exhaustive
    }

    override fun onCourseClicked(item: Course) {
        //TODO: dialogfragment, hogy biztos-e
        viewModel.addCourseToLocalCourses(item)
    }
}
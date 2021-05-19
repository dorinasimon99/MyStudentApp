package hu.bme.aut.mystudentapp.ui.courses.searchcourses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.courses.CoursesListAdapter
import hu.bme.aut.mystudentapp.ui.suredialog.SureDialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_courses.*
import kotlinx.android.synthetic.main.fragment_search_courses.*

class SearchCoursesFragment : RainbowCakeFragment<SearchCoursesViewState, SearchCoursesViewModel>(), CoursesListAdapter.CourseItemClickListener{

    private var coursesAdapter : CoursesListAdapter? = null

    override fun getViewResource() = R.layout.fragment_search_courses

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchCourses()

        coursesAdapter = CoursesListAdapter(this, ArrayList())

        searchViewCourse.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                coursesAdapter?.filter?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

    }

    override fun render(viewState: SearchCoursesViewState) {
        when(viewState){
            SearchCoursesInitial -> {
                (activity as MainActivity).supportActionBar?.title = "All courses"
            }
            is ListSearchCourses -> {
                coursesAdapter = CoursesListAdapter(this, viewState.courses)
                courses_list.adapter = coursesAdapter
                coursesAdapter?.submitList(viewState.courses)
            }
            ListCourseAdded -> {}
            is SearchCoursesError -> {
                Log.e("SearchCoursesFragment", viewState.e.toString())
            }
        }.exhaustive
    }

    private lateinit var itemClicked : Course
    override fun onCourseClicked(item: Course) {
        itemClicked = item
        val sureDialogFragment = SureDialogFragment("Course will be added to your course list. Are you sure?")
        sureDialogFragment.setTargetFragment(this, 0)
        sureDialogFragment.show(requireFragmentManager(), "SureDialogFragment")
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?){
        if(requestCode == SureDialogFragment.REQUEST_CODE){
            viewModel.addCourseToLocalCourses(itemClicked)
            findNavController().navigate(R.id.coursesFragment)
        }
    }
}
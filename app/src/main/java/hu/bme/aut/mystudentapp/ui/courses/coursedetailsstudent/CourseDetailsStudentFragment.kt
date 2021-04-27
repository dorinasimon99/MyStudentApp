package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_teachers.*
import kotlinx.android.synthetic.main.content_todos.*
import kotlinx.android.synthetic.main.fragment_course_details_student.*

class CourseDetailsStudentFragment : RainbowCakeFragment<CourseDetailsViewState, CourseDetailsViewModel>(), TodoRecyclerViewAdapter.TodoClickListener,
TeacherRecyclerViewAdapter.TeacherClickListener{

    private lateinit var adapter : TodoRecyclerViewAdapter
    private lateinit var adapter1 : TeacherRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onDetach()
                findNavController().navigate(R.id.coursesFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(todos_list, teachers_list)

        viewModel.load()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, recyclerView1: RecyclerView){
        UserDataBackend.todos().observe(requireActivity(), { todos ->
            adapter = TodoRecyclerViewAdapter(todos, this, requireContext())
            recyclerView.adapter = adapter
        })

        UserDataBackend.teachers().observe(requireActivity(), { teachers ->
            adapter1 = TeacherRecyclerViewAdapter(teachers, this)
            recyclerView1.adapter = adapter1
        })
    }

    override fun getViewResource() = R.layout.fragment_course_details_student

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: CourseDetailsViewState) {
        when(viewState){
            CourseDetailsInitial -> {
                (activity as MainActivity).supportActionBar?.title =
                    "${arguments?.getString("selectedCourseName")}"
                val courseName = arguments?.getString("selectedCourseName")
                if(courseName != null){
                    viewModel.loadTeachers(courseName)
                } else Log.e("CourseDetailsStudentFragment", "Coursename is null!")
            }
            CourseDetailsTeachers -> {}
            CourseDetailsError -> {}
        }.exhaustive
    }

    override fun onTodoClicked(pos: Int) {
        //TODO: kattintasra dialogfragment, hogy a kipipalassal torlodni is fog, biztos-e
        viewModel.deleteTodo(pos)
    }

    override fun onTeacherClicked(item: String?) {
        val bundle = bundleOf("element" to item)
        findNavController().navigate(R.id.action_courseDetailsStudentFragment_to_teacherRatesFragment, bundle)
    }
}
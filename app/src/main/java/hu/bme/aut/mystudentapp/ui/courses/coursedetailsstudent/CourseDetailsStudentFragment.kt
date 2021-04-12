package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.content_todos.*
import kotlinx.android.synthetic.main.fragment_course_details_student.*

class CourseDetailsStudentFragment : RainbowCakeFragment<CourseDetailsViewState, CourseDetailsViewModel>(), TodoRecyclerViewAdapter.TodoClickListener {

    private var element : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(todos_list)

        val teachers = ArrayList<String>()
        teachers.add("Teacher1")
        teachers.add("Teacher2")
        teachers.add("Teacher3")
        teachers.add("Teacher4")
        teachers.add("Teacher5")
        teachers.add("Teacher6")
        teachers.add("Teacher7")
        teachers.add("Teacher8")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, teachers)
        teachers_list_view.adapter = adapter

        teachers_list_view.setOnItemClickListener { _, _, position, _ ->
            element = adapter.getItem(position)
            val bundle = bundleOf("element" to element)
            findNavController().navigate(R.id.action_courseDetailsStudentFragment_to_teacherRatesFragment, bundle)
        }

        viewModel.load()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        //TODO: kattintasra dialogfragment, hogy a kipipalassal torlodni is fog, biztos-e
        UserDataBackend.todos().observe(requireActivity(), { todos ->
            recyclerView.adapter = TodoRecyclerViewAdapter(todos, this, requireContext())
        })
    }

    override fun getViewResource() = R.layout.fragment_course_details_student

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: CourseDetailsViewState) {
        when(viewState){
            CourseDetailsInitial -> {
                (activity as MainActivity).supportActionBar?.title =
                    "${arguments?.getString("selectedCourseName")}"
            }
            CourseDetailsError -> {}
        }.exhaustive
    }

    override fun onTodoClicked(pos: Int) {
        viewModel.deleteTodo(pos)
    }
}
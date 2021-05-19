package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.add_dialogs.AddTodoDialogFragment
import hu.bme.aut.mystudentapp.ui.suredialog.SureDialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_teachers.*
import kotlinx.android.synthetic.main.content_todos.*
import kotlinx.android.synthetic.main.fragment_course_details_student.*
import java.util.*

class CourseDetailsStudentFragment : RainbowCakeFragment<CourseDetailsViewState, CourseDetailsStudentViewModel>(),
TeacherListAdapter.TeacherClickListener, TodoListAdapter.TodoClickListener{

    private lateinit var todoAdapter: TodoListAdapter
    private lateinit var teacherAdapter: TeacherListAdapter

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

        todoAdapter = TodoListAdapter(this, requireContext())
        todos_list.adapter = todoAdapter

        teacherAdapter = TeacherListAdapter(this)
        teachers_list.adapter = teacherAdapter

        val courseCode = arguments?.getString("selectedCourseCode")
        val courseName = arguments?.getString("selectedCourseName")

        viewModel.load(courseCode!!, courseName!!)

        btnAddTodo.setOnClickListener {
            val addTodoDialog = AddTodoDialogFragment()
            addTodoDialog.setTargetFragment(this, 1)
            addTodoDialog.show(requireFragmentManager(), "AddTodoDialogFragment")
        }
    }

    override fun getViewResource() = R.layout.fragment_course_details_student

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: CourseDetailsViewState) {
        when(viewState){
            CourseDetailsInitial -> {
                (activity as MainActivity).supportActionBar?.title =
                    "${arguments?.getString("selectedCourseName")}"
            }
            is CourseDetailsLoading -> {
                todoAdapter.submitList(viewState.todos)
                teacherAdapter.submitList(viewState.teachers)
            }
            is CourseDetailsModifyTodos -> {
                todoAdapter.submitList(null)
                todoAdapter.submitList(viewState.todos)
            }
            CourseDetailsError -> {}
        }.exhaustive
    }

    private var todoClicked : Int = 0
    override fun onTodoClicked(pos: Int) {
        todoClicked = pos
        val sureDialogFragment = SureDialogFragment("Todo will be deleted from list. Are you sure?")
        sureDialogFragment.setTargetFragment(this, 0)
        sureDialogFragment.show(requireFragmentManager(), "SureDialogFragment")
    }

    override fun onTeacherClicked(pos: String?) {
        val bundle = bundleOf("element" to pos)
        findNavController().navigate(R.id.action_courseDetailsStudentFragment_to_teacherRatesFragment, bundle)
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?){
        if(requestCode == SureDialogFragment.REQUEST_CODE){
            viewModel.deleteTodo(todoClicked)
        }
        else if(requestCode == AddTodoDialogFragment.REQUEST_CODE){
            val text = data!!.getStringExtra(AddTodoDialogFragment.TODO_TEXT).toString()
            val newTodo = arguments?.getString("selectedCourseCode")?.let {
                Todo(UUID.randomUUID().toString(), text, false, UserDataBackend.currentUser.id,
                    it
                )
            }
            viewModel.addTodo(newTodo!!)
        }
    }
}
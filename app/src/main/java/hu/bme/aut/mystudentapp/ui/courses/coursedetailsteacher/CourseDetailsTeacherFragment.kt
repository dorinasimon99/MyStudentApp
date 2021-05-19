package hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.add_dialogs.AddTodoDialogFragment
import hu.bme.aut.mystudentapp.ui.suredialog.SureDialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_lessons.*
import kotlinx.android.synthetic.main.single_lesson.*
import java.util.*

class CourseDetailsTeacherFragment : RainbowCakeFragment<CourseDetailsTeacherViewState, CourseDetailsTeacherViewModel>(),
LessonsListAdapter.LessonsListener{

    private lateinit var lessonAdapter: LessonsListAdapter

    override fun getViewResource() = R.layout.fragment_course_details_teacher

    override fun provideViewModel() = getViewModelFromFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseName = arguments?.get("selectedCourseName").toString()

        viewModel.load(UserDataBackend.currentUser.name, courseName)

        lessonAdapter = LessonsListAdapter()

        lessons_list.adapter = lessonAdapter

        btnAddLessonTodo.setOnClickListener {
            val addTodoDialog = AddTodoDialogFragment()
            addTodoDialog.setTargetFragment(this, 1)
            addTodoDialog.show(requireFragmentManager(), "AddTodoDialogFragment")
        }
    }

    override fun render(viewState: CourseDetailsTeacherViewState) {
        when(viewState){
            CourseDetailsTeacherInitial -> {
                (activity as MainActivity).supportActionBar?.title = arguments?.get("selectedCourseName").toString()
            }
            is CourseDetailsTeacherLessons -> {
                lessonAdapter.submitList(viewState.lesson)
            }
            is CourseDetailsTeacherError -> {
                Toast.makeText(requireContext(), "Error: ${viewState.e.message}", Toast.LENGTH_LONG).show()
            }
        }.exhaustive
    }

    override fun onLessonItemSelected(item: String) {
        if(tbtnLessonTitle.isChecked) {
            layoutLessonTodos.visibility = View.VISIBLE
            layoutLessonQuizzes.visibility = View.VISIBLE
        }
        else {
            layoutLessonTodos.visibility = View.GONE
            layoutLessonQuizzes.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?){
        if(requestCode == AddTodoDialogFragment.REQUEST_CODE){
            val text = data!!.getStringExtra(AddTodoDialogFragment.TODO_TEXT).toString()
            val newTodo = arguments?.getString("selectedCourseCode")?.let {
                Todo(
                    UUID.randomUUID().toString(), text, false, UserDataBackend.currentUser.id,
                    it
                )
            }
        }
    }

}
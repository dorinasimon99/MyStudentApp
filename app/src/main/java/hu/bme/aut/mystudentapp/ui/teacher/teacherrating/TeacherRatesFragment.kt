package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.google.android.play.core.splitinstall.c
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.StudentComment
import hu.bme.aut.mystudentapp.data.model.TeacherRate
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.bme.aut.mystudentapp.ui.add_dialogs.AddCommentDialog
import hu.bme.aut.mystudentapp.ui.add_dialogs.AddRateDialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.fragment_teacher_rates.*
import java.util.*
import kotlin.collections.ArrayList

class TeacherRatesFragment : RainbowCakeFragment<TeacherRatesViewState, TeacherRatesViewModel>(){

    private var teachername = ""

    private var courses = ArrayList<String>()

    private lateinit var ratesAdapter: RateListAdapter

    private lateinit var commentsAdapter: CommentListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teachername = arguments?.getString("element").toString()
        viewModel.load(teachername)

        ratesAdapter = RateListAdapter()
        teacher_rates_list.adapter = ratesAdapter

        val horizontal = GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false)
        teacher_rates_list.layoutManager = horizontal

        commentsAdapter = CommentListAdapter()
        comments_list.adapter = commentsAdapter

        btnAddComment.setOnClickListener {
            val addCommentDialog = AddCommentDialog(courses)
            addCommentDialog.setTargetFragment(this, 0)
            addCommentDialog.show(requireFragmentManager(), "AddCommentDialogFragment")
        }

        btnAddRate.setOnClickListener {
            val addRateDialog = AddRateDialogFragment(courses)
            addRateDialog.setTargetFragment(this, 1)
            addRateDialog.show(requireFragmentManager(), "AddRateDialogFragment")
        }
    }

    override fun getViewResource() = R.layout.fragment_teacher_rates

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: TeacherRatesViewState) {
        when(viewState){
            TeacherRatesInitial -> {
                (activity as MainActivity).supportActionBar?.title = "Ratings"
                tvTeacherName.text = teachername
            }
            is TeacherCoursesLoaded -> {
                Log.i("TeacherRatesFragment", "Added courses: ${viewState.courses}")
                for(course in viewState.courses){
                    courses.add(course)
                }
            }
            is TeacherRatesAndCommentsLoaded -> {
                ratesAdapter.submitList(viewState.rates)
                commentsAdapter.submitList(viewState.comments)
            }
            is TeacherRatesUpdated -> {
                ratesAdapter.submitList(null)
                ratesAdapter.submitList(viewState.rates)
            }
            is TeacherCommentsUpdated -> {
                commentsAdapter.submitList(null)
                commentsAdapter.submitList(viewState.comments)
            }
            TeacherRatesError -> {
                Toast.makeText(context, "TeacherRatesError", Toast.LENGTH_LONG).show()
            }
        }.exhaustive
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == AddCommentDialog.REQUEST_CODE){
            val comment = StudentComment(UUID.randomUUID().toString(),
                data!!.getStringExtra(AddCommentDialog.COMMENT_NAME).toString(),
                data.getStringExtra(AddCommentDialog.COMMENT_TEXT).toString(),
                data.getStringExtra(AddCommentDialog.COURSE_NAME).toString(),
                teachername)
            viewModel.addComment(comment)
        }
        else if(requestCode == AddRateDialogFragment.REQUEST_CODE){
            val courseName = data!!.getStringExtra(AddRateDialogFragment.RATING_COURSE).toString()
            val rating = data.getStringExtra(AddRateDialogFragment.RATING_VALUE).toString()
            val rate = TeacherRate(teachername, courseName, rating)
            viewModel.addRating(rate)
        }
    }
}
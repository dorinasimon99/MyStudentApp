package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.StudentComment
import hu.bme.aut.mystudentapp.data.model.TeacherRate
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.content_comments.*
import kotlinx.android.synthetic.main.fragment_teacher_rates.*

class TeacherRatesFragment : RainbowCakeFragment<TeacherRatesViewState, TeacherRatesViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews(teacher_rates_list, comments_list)

        viewModel.load()
    }

    private fun setupRecyclerViews(recyclerView1: RecyclerView, recyclerView2: RecyclerView){
        UserDataBackend.rates().observe(requireActivity(), Observer<MutableList<TeacherRate>> { rates ->
            recyclerView1.adapter = RateRecyclerViewAdapter(rates)
        })
        UserDataBackend.comments().observe(requireActivity(), Observer<MutableList<StudentComment>> { comments ->
            recyclerView2.adapter = CommentRecyclerViewAdapter(comments)
        })

        val horizontal = GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false)
        recyclerView1.layoutManager = horizontal
    }

    override fun getViewResource() = R.layout.fragment_teacher_rates

    override fun provideViewModel() = getViewModelFromFactory()

    override fun render(viewState: TeacherRatesViewState) {
        when(viewState){
            TeacherRatesInitial -> {
                (activity as MainActivity).supportActionBar?.title = "Ratings"
                tvTeacherName.text = arguments?.getString("element")
            }
            TeacherRatesError -> {}
        }.exhaustive
    }
}
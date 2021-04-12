package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.TeacherRate
import hu.mystudentapp.R

class RateRecyclerViewAdapter(
    private val rates: MutableList<TeacherRate>?
) : RecyclerView.Adapter<RateRecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_teacher_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = rates?.get(position)
        holder.className.text = item?.courseName
        holder.rate.text = item?.rating

    }

    override fun getItemCount() = rates?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val className: TextView = view.findViewById(R.id.tvCourseNameTeacherRate)
        val rate : TextView = view.findViewById(R.id.tvRate)
    }
}
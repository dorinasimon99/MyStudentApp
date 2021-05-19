package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.mystudentapp.R

class TeacherRecyclerViewAdapter(
    private val values: List<String>?,
    private val teacherClickListener: TeacherClickListener
): RecyclerView.Adapter<TeacherRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_teacher, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.teacherName.text = item

        holder.item = item
    }

    interface TeacherClickListener {
        fun onTeacherClicked(pos: String?)
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teacherName : TextView = view.findViewById(R.id.tvTeacherName)

        var item: String? = null

        init {
            teacherName.setOnClickListener {
                item?.let {
                    teacherClickListener.onTeacherClicked(item)
                }
            }
        }
    }
}
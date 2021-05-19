package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.mystudentapp.R

class TeacherListAdapter(val teacherClickListener: TeacherClickListener)
    : ListAdapter<Teacher, TeacherListAdapter.TeacherViewHolder>(TeacherItemsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_teacher, parent, false)
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface TeacherClickListener {
        fun onTeacherClicked(pos: String?)
    }

    inner class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teacherName : TextView = view.findViewById(R.id.tvTeacherName)

        var teacherItem: Teacher? = null

        init {
            teacherName.setOnClickListener {
                teacherItem?.let {
                    teacherClickListener.onTeacherClicked(teacherItem?.name)
                }
            }
        }

        fun bind(item: Teacher){
            teacherItem = item

            teacherName.text = item.name
        }
    }

}

data class Teacher(
    val name: String
)

object TeacherItemsComparator : DiffUtil.ItemCallback<Teacher>(){
    override fun areItemsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
        return oldItem == newItem
    }

}
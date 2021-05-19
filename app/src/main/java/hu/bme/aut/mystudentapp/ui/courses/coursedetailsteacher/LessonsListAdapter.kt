package hu.bme.aut.mystudentapp.ui.courses.coursedetailsteacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.single_lesson.view.*

class LessonsListAdapter : ListAdapter<Lesson, LessonsListAdapter.LessonViewHolder>(LessonItemsComparator) {

    var listener: LessonsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsListAdapter.LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_lesson, parent, false)
        listener = (parent.context) as LessonsListener
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonsListAdapter.LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val lessonNameToggle: ToggleButton = itemView.tbtnLessonTitle

        private var lessonsItem: Lesson? = null

        init {
            lessonNameToggle.setOnClickListener {
                lessonsItem?.let { listener?.onLessonItemSelected(lessonsItem!!.name) }
            }
        }

        fun bind(item: Lesson){
            lessonsItem = item

            lessonNameToggle.textOff = item.name
            lessonNameToggle.textOn = item.name
        }
    }

    interface LessonsListener {
        fun onLessonItemSelected(item: String)
    }
}

data class Lesson (
    val name: String
)

object LessonItemsComparator : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
        return oldItem == newItem
    }
}
package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.TeacherRate
import hu.mystudentapp.R


class RateListAdapter : ListAdapter<TeacherRate, RateListAdapter.RatesViewHolder>(RateItemsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_teacher_rate, parent, false)
        return RatesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RateListAdapter.RatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val className: TextView = itemView.findViewById(R.id.tvCourseNameTeacherRate)
        val rate : TextView = itemView.findViewById(R.id.tvRate)

        var rateItem: TeacherRate? = null

        fun bind(item: TeacherRate){
            rateItem = item

            className.text = item.courseName
            rate.text = item.rating
        }
    }
}

object RateItemsComparator : DiffUtil.ItemCallback<TeacherRate>() {
    override fun areItemsTheSame(oldItem: TeacherRate, newItem: TeacherRate): Boolean {
        return oldItem.teacherName == newItem.teacherName && oldItem.courseName == newItem.courseName && oldItem.rating == newItem.rating
    }

    override fun areContentsTheSame(oldItem: TeacherRate, newItem: TeacherRate): Boolean {
        return oldItem == newItem
    }

}
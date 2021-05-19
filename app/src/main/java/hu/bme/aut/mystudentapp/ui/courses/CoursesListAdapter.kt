package hu.bme.aut.mystudentapp.ui.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.Course
import hu.mystudentapp.R
import java.util.*
import kotlin.collections.ArrayList


class CoursesListAdapter(
    private val listener: CourseItemClickListener,
    private val values: List<Course>?)
    : ListAdapter<Course, CoursesListAdapter.CourseViewHolder>(CourseItemsComparator),
    Filterable {

    private val items = mutableListOf<Course>()

    var courseFilterList : List<Course>? = listOf()

    init {
        courseFilterList = values
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_class, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: Button = view.findViewById(R.id.btnClassName)
        val classTimeView: TextView = view.findViewById(R.id.tvClassTime)
        val classCodeView : TextView = view.findViewById(R.id.tvClassCode)
        val classCreditView : TextView = view.findViewById(R.id.tvClassCredit)

        var courseItem: Course? = null

        init {
            nameView.setOnClickListener {
                courseItem?.let {
                    val newCourse = it.copy()
                    courseItem = newCourse
                    listener.onCourseClicked(newCourse)
                }
            }
        }

        fun bind(item: Course){
            courseItem = item

            nameView.text = item.name
            classTimeView.text = item.time
            classCodeView.text = item.courseCode
            classCreditView.text = item.credits.toString()
        }
    }

    interface CourseItemClickListener {
        fun onCourseClicked(item: Course)
    }

    fun addItem(item: Course){
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if(charSearch.isEmpty()){
                    courseFilterList = values
                } else {
                    val resultList = arrayListOf<Course>()
                    if (values != null) {
                        for(course in values){
                            if(course.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(
                                    Locale.ROOT))
                                || course.courseCode.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(
                                    Locale.ROOT))){
                                resultList.add(course)
                            }
                        }
                        courseFilterList = resultList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = courseFilterList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                courseFilterList = results?.values as ArrayList<Course>
                submitList(courseFilterList)
            }
        }
    }
}

object CourseItemsComparator : DiffUtil.ItemCallback<Course>(){
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }

}
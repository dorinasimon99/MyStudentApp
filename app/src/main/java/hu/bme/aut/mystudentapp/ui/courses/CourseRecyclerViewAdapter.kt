package hu.bme.aut.mystudentapp.ui.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.Course
import hu.bme.aut.mystudentapp.data.model.LocalCourseEntity
import hu.mystudentapp.R
import java.util.*
import kotlin.collections.ArrayList

class CourseRecyclerViewAdapter (
    private val values: List<Course>?,
    private val listener: CourseItemClickListener
    )
    : RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>(), Filterable {

        private val items = mutableListOf<Course>()

        var courseFilterList : List<Course>? = listOf()

        init {
            courseFilterList = values
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_class, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item = values?.get(position)
            holder.nameView.text = courseFilterList?.get(position)?.name
            holder.classTimeView.text = courseFilterList?.get(position)?.time
            holder.classCodeView.text = courseFilterList?.get(position)?.courseCode
            holder.classCreditView.text = courseFilterList?.get(position)?.credits.toString()

            holder.item = item

        }

        interface CourseItemClickListener {
            fun onCourseClicked(item: Course)
        }

        override fun getItemCount() = courseFilterList?.size ?: 0

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: Button = view.findViewById(R.id.btnClassName)
            val classTimeView: TextView = view.findViewById(R.id.tvClassTime)
            val classCodeView : TextView = view.findViewById(R.id.tvClassCode)
            val classCreditView : TextView = view.findViewById(R.id.tvClassCredit)

            var item: Course? = null

            init {
                nameView.setOnClickListener {
                    item?.let {
                        val newCourse = it.copy()
                        item = newCourse
                        listener.onCourseClicked(newCourse)
                    }
                }
            }
        }

        fun addItem(item: Course){
            items.add(item)
            notifyItemInserted(items.size - 1)
        }

        fun update(courses: List<Course>){
            items.clear()
            items.addAll(courses)
            notifyDataSetChanged()
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
                            if(course.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))
                                || course.courseCode.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))){
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
                notifyDataSetChanged()
            }
        }
    }
}
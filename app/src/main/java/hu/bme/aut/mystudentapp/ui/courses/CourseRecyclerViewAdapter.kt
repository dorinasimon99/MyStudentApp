package hu.bme.aut.mystudentapp.ui.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.Course
import hu.mystudentapp.R

class CourseRecyclerViewAdapter (
    private val values: MutableList<Course>?
    ) :
    RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_class, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item = values?.get(position)
            holder.nameView.text = item?.name
            holder.classTimeView.text = item?.time
            holder.classCodeView.text = item?.courseCode
            holder.classCreditView.text = item?.credits.toString()

        }

        override fun getItemCount() = values?.size ?: 0

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameView: Button = view.findViewById(R.id.btnClassName)
            val classTimeView: TextView = view.findViewById(R.id.tvClassTime)
            val classCodeView : TextView = view.findViewById(R.id.tvClassCode)
            val classCreditView : TextView = view.findViewById(R.id.tvClassCredit)
        }
}
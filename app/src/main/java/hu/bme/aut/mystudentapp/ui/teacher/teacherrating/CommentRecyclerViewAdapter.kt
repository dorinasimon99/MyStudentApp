package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.StudentComment
import hu.mystudentapp.R

class CommentRecyclerViewAdapter(
    val comments: MutableList<StudentComment>?
) : RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = comments?.get(position)
        holder.name.text = item?.name
        holder.comment.text = item?.comment

    }

    override fun getItemCount() = comments?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvCommentName)
        val comment : TextView = view.findViewById(R.id.tvComment)
    }
}
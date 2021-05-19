package hu.bme.aut.mystudentapp.ui.teacher.teacherrating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import hu.bme.aut.mystudentapp.data.model.StudentComment
import hu.mystudentapp.R


class CommentListAdapter : ListAdapter<StudentComment, CommentListAdapter.CommentViewHolder>(CommentItemsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tvCommentName)
        val comment : TextView = itemView.findViewById(R.id.tvComment)
        val course: Chip = itemView.findViewById(R.id.courseChip)

        var commentItem: StudentComment? = null

        fun bind(item: StudentComment){
            commentItem = item

            name.text = item.name
            comment.text = item.comment
            course.text = item.courseName
        }
    }
}

object CommentItemsComparator : DiffUtil.ItemCallback<StudentComment>(){
    override fun areItemsTheSame(oldItem: StudentComment, newItem: StudentComment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StudentComment, newItem: StudentComment): Boolean {
        return oldItem == newItem
    }

}
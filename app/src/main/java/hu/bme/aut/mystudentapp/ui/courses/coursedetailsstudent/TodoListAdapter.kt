package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.single_todo.view.*

class TodoListAdapter(val todoClickListener: TodoClickListener, val context: Context)
    : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(TodoItemsComparator) {

    var checkAnim : Animation? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_todo, parent, false)
        checkAnim = AnimationUtils.loadAnimation(context, R.anim.check_animation)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val todoText: TextView = itemView.tvTodo
        private val imgBtnCheck: ImageButton = itemView.imgBtnCheck

        private var todoItem: Todo? = null

        init {
            imgBtnCheck.setOnClickListener {
                imgBtnCheck.startAnimation(checkAnim)
                imgBtnCheck.drawable.setTint(Color.rgb(92, 204, 55))
                todoItem?.let {
                    val newTodo = it.copy(done = true)
                    todoItem = newTodo
                    todoClickListener.onTodoClicked(this.adapterPosition)
                }
            }
        }

        fun bind(item: Todo){
            todoItem = item

            todoText.text = item.name
        }
    }

    interface TodoClickListener {
        fun onTodoClicked(pos: Int)
    }

}

object TodoItemsComparator : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}
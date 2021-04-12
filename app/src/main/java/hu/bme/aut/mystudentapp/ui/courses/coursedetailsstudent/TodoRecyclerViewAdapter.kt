package hu.bme.aut.mystudentapp.ui.courses.coursedetailsstudent

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.mystudentapp.data.model.Todo
import hu.mystudentapp.R
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import kotlin.coroutines.coroutineContext

class TodoRecyclerViewAdapter(
    private val values: MutableList<Todo>?,
    private val todoClickListener: TodoClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder>() {


    val checkAnim = AnimationUtils.loadAnimation(context, R.anim.check_animation)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.todoName.text = item?.name

        holder.item = item
    }

    interface TodoClickListener {
        fun onTodoClicked(pos: Int)
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoName : TextView = view.findViewById(R.id.tvTodo)
        val imgBtnCheck : ImageButton = view.findViewById(R.id.imgBtnCheck)

        var item: Todo? = null

        init {
            imgBtnCheck.setOnClickListener {
                imgBtnCheck.startAnimation(checkAnim)
                imgBtnCheck.drawable.setTint(Color.rgb(92, 204, 55))
                item?.let {
                    val newTodo = it.copy(done = true)
                    item = newTodo
                    todoClickListener.onTodoClicked(this.adapterPosition)
                }
            }
        }
    }
}
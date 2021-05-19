package hu.bme.aut.mystudentapp.ui.add_dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.dialog_add_todo.*
import kotlinx.android.synthetic.main.dialog_add_todo.view.*

class AddTodoDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_add_todo, container, false)

        view.btnTodoOk.setOnClickListener {
            sendResult(1)
            this.dismiss()
        }

        view.btnTodoCancel.setOnClickListener {
            this.dismiss()
        }

        return view
    }

    private fun sendResult(REQUEST_CODE: Int){
        val intent = Intent()
        intent.putExtra(TODO_TEXT, etTodoText.text.toString())
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }

    companion object{
        val TODO_TEXT = "Todo text"
        var REQUEST_CODE = 1
    }
}
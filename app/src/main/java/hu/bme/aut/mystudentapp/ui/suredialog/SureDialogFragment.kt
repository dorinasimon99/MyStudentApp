package hu.bme.aut.mystudentapp.ui.suredialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.dialog_sure.view.*

class SureDialogFragment(val text: String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_sure, container, false)

        view.tvAreYouSure.text = text

        view.btnSureOk.setOnClickListener {
            sendResult(0)
            this.dismiss()
        }

        view.btnSureCancel.setOnClickListener {
            this.dismiss()
        }

        return view
    }

    private fun sendResult(REQUEST_CODE : Int) {
        val intent = Intent()
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }

    companion object {
        var REQUEST_CODE = 0
    }
}
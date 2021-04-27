package hu.bme.aut.mystudentapp.ui.timepickerdialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.play.core.splitinstall.f
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.dialog_timepicker_range.*
import kotlinx.android.synthetic.main.dialog_timepicker_range.view.*

class TimePickerRangeDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_timepicker_range, container, false)

        view.timePickerStart.setIs24HourView(true)

        view.timePickerEnd.setIs24HourView(true)

        view.btnTimeOk.setOnClickListener {
            sendResult(0)
            this.dismiss()
        }

        view.btnTimeCancel.setOnClickListener {
            this.dismiss()
        }

        return view
    }

    private fun sendResult(REQUEST_CODE : Int) {
        val intent = Intent()
        val startHour = if(timePickerStart.hour < 10){
            "0${timePickerStart.hour}"
        }else timePickerStart.hour
        val endHour = if(timePickerEnd.hour < 10){
            "0${timePickerEnd.hour}"
        }else timePickerEnd.hour
        val startMinute = if(timePickerStart.minute < 10){
            "0${timePickerStart.minute}"
        }else timePickerStart.minute
        val endMinute = if(timePickerEnd.minute < 10){
            "0${timePickerEnd.minute}"
        }else timePickerEnd.minute
        intent.putExtra(START_TIME_BUNDLE_EXTRA, "$startHour:$startMinute")
        intent.putExtra(END_TIME_BUNDLE_EXTRA, "$endHour:$endMinute")
        targetFragment?.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }

    companion object{
        val START_TIME_BUNDLE_EXTRA = "Start time bundle extra"
        val END_TIME_BUNDLE_EXTRA = "End time bundle extra"
        var REQUEST_CODE = 0
    }
}
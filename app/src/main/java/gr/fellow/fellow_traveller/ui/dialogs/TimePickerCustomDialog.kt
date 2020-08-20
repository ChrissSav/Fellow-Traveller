package gr.fellow.fellow_traveller.ui.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import gr.fellow.fellow_traveller.R
import java.util.*

class TimePickerCustomDialog(
    private val buttonText: String,
    private val listener: (String) -> Unit
) :

    DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var hour = 0
    private var min = 0
    private lateinit var calendar: Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (buttonText.isNullOrBlank()) {
            hour = 12
            min = 0
        } else {
            hour = buttonText.substring(0, 2).toInt()
            min = buttonText.substring(3).toInt()
        }
        return TimePickerDialog(
            activity,
            R.style.DialogCostumeTheme, this, hour, min, true
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val time =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)

        listener(time)
    }

}
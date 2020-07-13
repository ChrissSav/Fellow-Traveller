package com.example.fellowtravellerbeta.ui.dialogs

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.newTrip.NewTripViewModel
import java.util.*

class DatePickerCustomDialog(
    private val buttonText: String,
    private val defaultTitle: String,
    private val viewModel: NewTripViewModel
) :

    DialogFragment(), OnDateSetListener {

    private var year = 0
    private var month = 0
    private var day = 0
    private lateinit var calendar: Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (buttonText == defaultTitle) {
            calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
        } else {
            day = buttonText.substring(0, 2).toInt()
            month = buttonText.substring(3, 5).toInt() - 1
            year = buttonText.substring(6).toInt()
        }
        val datePickerDialog = DatePickerDialog(
            requireActivity(), R.style.DialogCostumeTheme, this, year, month, day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        val date =
            java.lang.String.format(Locale.getDefault(), "%02d/%02d/%04d", day, (month + 1), year)

        viewModel.applyDate(date)
    }


}
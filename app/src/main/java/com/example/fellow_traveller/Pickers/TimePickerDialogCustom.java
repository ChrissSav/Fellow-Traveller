package com.example.fellow_traveller.Pickers;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.fellow_traveller.R;

import java.util.Calendar;

public class TimePickerDialogCustom extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    private int hour;
    private int min;
    private EditText editText;
    private Calendar calendar;


    public TimePickerDialogCustom(EditText editText) {
        this.editText = editText;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (editText.getText().length() < 1) {
            calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
        } else {
            hour = Integer.parseInt(editText.getText().toString().substring(0, 2));
            min = Integer.parseInt(editText.getText().toString().substring(3));
        }

        android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getActivity(),
                R.style.DialogCostumeTheme, this, hour, min, true);


        return timePickerDialog;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourString, minString;
        hour = hourOfDay;
        min = minute;
        if (hourOfDay <= 9) {
            hourString = "0" + hourOfDay;
        } else {
            hourString = hourOfDay + "";
        }
        if (minute <= 9) {
            minString = "0" + minute;
        } else {
            minString = minute + "";
        }
        editText.setText(hourString + ":" + minString);

    }


}

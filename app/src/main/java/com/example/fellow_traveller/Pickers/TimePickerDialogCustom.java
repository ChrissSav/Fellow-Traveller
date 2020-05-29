package com.example.fellow_traveller.Pickers;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.fellow_traveller.R;

import java.util.Calendar;

public class TimePickerDialogCustom extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    private int hour;
    private int min;
    private Button button;
    private Calendar calendar;
    private String defaultTitle;


    public TimePickerDialogCustom(Button button,String title) {
        this.button = button;
        defaultTitle = title;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (button.getText().equals(defaultTitle)) {
            hour = 12;
            min = 0;
        } else {
            hour = Integer.parseInt(button.getText().toString().substring(0, 2));
            min = Integer.parseInt(button.getText().toString().substring(3));
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
        button.setText(hourString + ":" + minString);

    }


}

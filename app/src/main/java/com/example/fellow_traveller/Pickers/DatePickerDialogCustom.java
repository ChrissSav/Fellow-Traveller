package com.example.fellow_traveller.Pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;


import com.example.fellow_traveller.R;

import java.util.Calendar;


public class DatePickerDialogCustom extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    private EditText editText;


    public DatePickerDialogCustom(EditText editText) {
        this.editText = editText;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        if (editText.getText().length() < 1) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            day = Integer.parseInt(editText.getText().toString().substring(0, 2));
            month = Integer.parseInt(editText.getText().toString().substring(3, 5)) -1;
            year = Integer.parseInt(editText.getText().toString().substring(6));

        }

        DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                R.style.DialogCostumeTheme, this, year, month, day);
        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        return datepickerdialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        month = month + 1;
        String monString, dayString;
        if (month <= 9) {
            monString = "0" + month;
        } else {
            monString = month + "";
        }
        if (day <= 9) {
            dayString = "0" + day;
        } else {
            dayString = day + "";
        }
        // TextView textview = (TextView)getActivity().findViewById(R.id.textView1);

        editText.setText(dayString + "/" + monString + "/" + year);

    }
}
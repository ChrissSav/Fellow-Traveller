package com.example.fellow_traveller.NewOffer;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NewOfferStage2Fragment extends Fragment {

    private View view;
    private TextInputLayout textInputLayout_date, textInputLayout_time;
    private String date = "";
    private String time = "";
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;

    public NewOfferStage2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage2, container, false);
        textInputLayout_date = view.findViewById(R.id.NewOfferStage2Fragment_TextInputLayout_date);
        textInputLayout_time = view.findViewById(R.id.NewOfferStage2Fragment_TextInputLayout_time);
        textInputLayout_date.getEditText().setText(date);
        textInputLayout_time.getEditText().setText(time);

        textInputLayout_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();

            }
        });


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mon, d;
                if (month <= 9) {
                    mon = "0" + month;
                } else {
                    mon = month + "";
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = day + "";
                }
                String ddate = d + "/" + mon + "/" + year;
                textInputLayout_date.getEditText().setText(ddate + "");
            }
        };

        textInputLayout_time.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mTimeListener, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();


            }
        });
        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour, min;
                if (hourOfDay <= 9) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = hourOfDay + "";
                }
                if (minute <= 9) {
                    min = "0" + minute;
                } else {
                    min = minute + "";
                }
                String tiime = hour + ":" + min;
                textInputLayout_time.getEditText().setText(tiime + "");
            }
        };
        return view;
    }

    public String toString() {
        return "NewOfferStage2Fragment";
    }

    public int getRank() {
        return 2;
    }

    @Override
    public void onDestroy() {
        // Log.i("textInputLayout_pass_1", "onDestroy");
        date = textInputLayout_date.getEditText().getText().toString();
        time = textInputLayout_time.getEditText().getText().toString();

        Long timestamp = getTimeStamp();
        Log.i("timestamptimestamp",timestamp+"");
        super.onDestroy();
    }

    public Boolean isOk() {
        if (textInputLayout_date.getEditText().getText().length() < 1) {
            textInputLayout_date.setError("Υποχρεωτικό πεδίο!");
            return false;
        } else {
            textInputLayout_date.setError(null);
        }
        if (textInputLayout_time.getEditText().getText().length() < 1) {
            textInputLayout_time.setError("Υποχρεωτικό πεδίο!");
            return false;
        } else {
            textInputLayout_time.setError(null);
        }
        return true;
    }

    public String getDate() {
        return textInputLayout_date.getEditText().getText().toString();
    }

    public String getTime() {
        return textInputLayout_time.getEditText().getText().toString();
    }

    public Long getTimeStamp() {
        try {
            String date_temp = textInputLayout_date.getEditText().getText().toString();
            String time_temp = textInputLayout_time.getEditText().getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date parsedDate = dateFormat.parse(date_temp+ " " + time_temp);
            return (parsedDate.getTime() / 1000);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(0);
    }

}

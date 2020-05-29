package com.example.fellow_traveller.NewOffer;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fellow_traveller.Pickers.DatePickerDialogCustom;
import com.example.fellow_traveller.Pickers.TimePickerDialogCustom;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Util.MaterialDatePickerBuilder;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Date;
import java.sql.Timestamp;

import static com.example.fellow_traveller.Util.SomeMethods.createSnackBar;
import static com.example.fellow_traveller.Util.SomeMethods.currentTimeStamp;
import static com.example.fellow_traveller.Util.SomeMethods.dateTimeToTimestamp;


public class NewOfferStage2Fragment extends Fragment {

    private View view;
    private Button buttonDate, buttonTime;
    private String DATE_TITLE = "Όρισε την ημερομηνία ... ";
    private String TIME_TITLE = "Όρισε την ώρα ... ";

    private String tempDate = "";
    private String tempTime = "";

    private DialogFragment timeDialog, dateDialog;

    public NewOfferStage2Fragment() {
        tempDate =  DATE_TITLE;
        tempTime =  TIME_TITLE;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage2, container, false);
        buttonDate = view.findViewById(R.id.NewOfferStage1Fragment_button_date);
        buttonTime = view.findViewById(R.id.NewOfferStage1Fragment_button_time);


        if (!tempDate.equals(DATE_TITLE))
            buttonDate.setText(tempDate);
        if (!tempTime.equals(TIME_TITLE))
            buttonTime.setText(tempTime);


        timeDialog = new TimePickerDialogCustom(buttonTime, TIME_TITLE);
        dateDialog = new DatePickerDialogCustom(buttonDate, DATE_TITLE);


        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show(getFragmentManager(), "dateDialog");


            }
        });


        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timeDialog.show(getFragmentManager(), "timeDialog");

            }
        });

        return view;
    }

    public String toString() {
        return "NewOfferStage2Fragment";
    }

    public int getRank() {
        return 3;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tempDate = buttonDate.getText().toString();
        tempTime = buttonTime.getText().toString();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!buttonDate.getText().equals(DATE_TITLE)) {
            buttonDate.setTextColor(getResources().getColor(R.color.black_color));

        }
        if (!buttonTime.getText().equals(TIME_TITLE)) {
            buttonTime.setTextColor(getResources().getColor(R.color.black_color));
        }
    }

    public Boolean validateFragment() {
        if (buttonTime.getText().equals(TIME_TITLE)) {
            createSnackBar(view, "Παρακαλώ επιλέξτε την ημερ/νια");
            return false;
        }
        if (buttonTime.getText().equals(TIME_TITLE)) {
            createSnackBar(view, "Παρακαλώ επιλέξτε την ώρα");
            return false;
        }

        Long timestamp = currentTimeStamp();
        if (!(getTimeStamp() - timestamp >= getResources().getInteger(R.integer.Time_difference))) {
            createSnackBar(view, getActivity().getResources().getString(R.string.ERROR_TRIP_TIMESTAMP));
            return false;
        }
        return true;
    }

    public String getDate() {
        return buttonDate.getText().toString();
    }

    public String getTime() {
        return buttonTime.getText().toString();
    }

    public Long getTimeStamp() {

        String date_temp = buttonDate.getText().toString();
        String time_temp = buttonTime.getText().toString();
        return dateTimeToTimestamp(date_temp, time_temp);
    }


}

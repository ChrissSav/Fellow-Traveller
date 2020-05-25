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
    private TextInputLayout textInputLayout_date, textInputLayout_time;
    private String date = "";
    private String time = "";
    private DatePickerDialog.OnDateSetListener mDateListener;
    private TimePickerDialog.OnTimeSetListener mTimeListener;
    private DialogFragment timeDialog, dateDialog;

    public NewOfferStage2Fragment() {

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


        timeDialog = new TimePickerDialogCustom(textInputLayout_time.getEditText());
        dateDialog = new DatePickerDialogCustom(textInputLayout_date.getEditText());


        textInputLayout_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show(getFragmentManager(), "dateDialog");


            }
        });


        textInputLayout_time.getEditText().setOnClickListener(new View.OnClickListener() {
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
        // Log.i("textInputLayout_pass_1", "onDestroy");
        date = textInputLayout_date.getEditText().getText().toString();
        time = textInputLayout_time.getEditText().getText().toString();

        super.onDestroy();
    }

    public Boolean validateFragment() {
        if (textInputLayout_date.getEditText().getText().length() < 1) {
            textInputLayout_date.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            textInputLayout_date.setError(null);
        }
        if (textInputLayout_time.getEditText().getText().length() < 1) {
            textInputLayout_time.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            textInputLayout_time.setError(null);
        }

        Long timestamp = currentTimeStamp();
        if (!(getTimeStamp() - timestamp >= getResources().getInteger(R.integer.Time_difference))) {
            createSnackBar(view, getActivity().getResources().getString(R.string.ERROR_TRIP_TIMESTAMP));
            return false;
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

        String date_temp = textInputLayout_date.getEditText().getText().toString();
        String time_temp = textInputLayout_time.getEditText().getText().toString();
        return dateTimeToTimestamp(date_temp, time_temp);
    }




}

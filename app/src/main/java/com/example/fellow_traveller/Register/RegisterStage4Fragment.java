package com.example.fellow_traveller.Register;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage4Fragment extends Fragment {

    private View view;
    private TextInputLayout textInputLayout_birthday;
    private String birthday = "";
    private DatePickerDialog.OnDateSetListener mDateListener;


    public RegisterStage4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_stage_4, container, false);
        textInputLayout_birthday = view.findViewById(R.id.RegisterStage4Fragment_TextInputLayout_birthday);
        textInputLayout_birthday.getEditText().setText(birthday);


        textInputLayout_birthday.getEditText().setOnClickListener(new View.OnClickListener() {

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
                textInputLayout_birthday.getEditText().setText(ddate + "");
            }
        };


        return view;
    }

    @Override
    public void onDestroy() {
        //Log.i("Lifecycle_Stage3", "onDestroy ");

        // Log.i("Lifecycle", "onDestroy");
        birthday = textInputLayout_birthday.getEditText().getText().toString();
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage4Fragment";
    }

    public boolean isOk() {
        String date = textInputLayout_birthday.getEditText().getText().toString();

        if (date.length() < 10) {
            textInputLayout_birthday.setError("Invalid date");
            return false;
        } else {
            textInputLayout_birthday.setError(null);
            return true;
        }

    }

    public int getRank() {
        return 4;
    }

}

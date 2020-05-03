package com.example.fellow_traveller.Register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.fellow_traveller.Util.InputValidation.isValidPhone;


public class RegisterStagePhoneFragment extends Fragment {

    private EditText editTextPhone;
    private View view;
    public RegisterStagePhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_register_stage_phone, container, false);

        editTextPhone = view.findViewById(R.id.RegisterStagePhoneFragment_editText_number);

        return  view;
    }


    public String toString() {
        return "RegisterStagePhoneFragment";
    }

    public String getPhone() {
        return editTextPhone.getText().toString();
    }

    public void setErrorToEditText(String msg) {
        editTextPhone.setError(msg);
    }

    public EditText getEditText() {
        return editTextPhone;
    }

    public int getRank() {
        return 1;
    }



}





















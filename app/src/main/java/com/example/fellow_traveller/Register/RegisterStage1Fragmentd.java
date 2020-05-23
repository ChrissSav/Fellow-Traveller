package com.example.fellow_traveller.Register;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterStage1Fragmentd extends Fragment {

    private TextInputLayout textInputLayoutEmail;
    private GlobalClass globalClass;
    private View view;

    public RegisterStage1Fragmentd() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        view = inflater.inflate(R.layout.fragment_user_register_stage_1, container, false);
        textInputLayoutEmail = view.findViewById(R.id.RegisterStage1Fragment_textInputLayout_email);




        return view;
    }

    public String toString() {
        return "RegisterStage1Fragment";
    }

    public int getRank() {
        return 1;
    }




    public String getEmail() {
        return textInputLayoutEmail.getEditText().getText().toString();
    }


    public EditText getEditText() {
        return textInputLayoutEmail.getEditText();
    }

    public void setErrorToEditText(String msg) {
        textInputLayoutEmail.setError(msg);
    }
}

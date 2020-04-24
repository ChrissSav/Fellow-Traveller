package com.example.fellow_traveller.Register;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.fellow_traveller.Utils.InputValidation.isValidEmail;

public class RegisterStage1Fragment extends Fragment {

    private TextInputLayout textInputLayoutEmail;

    private View view;

    public RegisterStage1Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_stage_1, container, false);
        textInputLayoutEmail = view.findViewById(R.id.RegisterStage1Fragment_textInputLayout_email);

        return view;
    }

    public String toString() {
        return "RegisterStage1Fragment";
    }

    public int getRank() {
        return 1;
    }

    public Boolean validateFragment() {
        //Log.i("isOk",textInputLayout.getEditText().getText().length()+"");
        if (textInputLayoutEmail.getEditText().getText().length() < 1) {
            textInputLayoutEmail.setError("Please check the email field !");
            return false;
        } else {
            if (isValidEmail(textInputLayoutEmail.getEditText().getText())) {
                textInputLayoutEmail.setError(null);
                return true;

            } else {
                textInputLayoutEmail.setError("Το μαιλ ειναι λάθος");
                return false;
            }
        }
    }

    public String getEmail(){
        return textInputLayoutEmail.getEditText().getText().toString();
    }

}

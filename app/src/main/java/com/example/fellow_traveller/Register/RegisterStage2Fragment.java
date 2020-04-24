package com.example.fellow_traveller.Register;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.fellow_traveller.Utils.InputValidation.isValidPassword;


public class RegisterStage2Fragment extends Fragment {
    private TextInputLayout textInputLayoutPassword, textInputLayoutPasswordConfirm;
    private View view;

    private String pass_1 = "aD@ffff1";
    private String pass_2 = "aD@ffff1";


    public RegisterStage2Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register_stage_2, container, false);
        textInputLayoutPassword = view.findViewById(R.id.RegisterStage2Fragment_textInputLayout_password_1);
        textInputLayoutPasswordConfirm = view.findViewById(R.id.RegisterStage2Fragment_textInputLayout_password_2);
        textInputLayoutPassword.getEditText().setText(pass_1);
        textInputLayoutPasswordConfirm.getEditText().setText(pass_2);
        return view;
    }

    @Override
    public void onDestroy() {
        pass_1 = textInputLayoutPassword.getEditText().getText().toString();
        pass_2 = textInputLayoutPasswordConfirm.getEditText().getText().toString();

        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage2Fragment";
    }

    public int getRank() {
        return 2;
    }
    public Boolean validateFragment() {
        String password = textInputLayoutPassword.getEditText().getText().toString();
        String passwordVerify = textInputLayoutPasswordConfirm.getEditText().getText().toString();

        // TODO check password length on
        // TODO check password complexity as user types in the first field.
        if (!isValidPassword(password) || password.length() < 6) {
            textInputLayoutPassword.setError("Password is not valid");
            return false;
        } else if (!password.equals(passwordVerify)) {
            textInputLayoutPassword.setError("The passwords is not the same");
            textInputLayoutPasswordConfirm.setError("The passwords is not the same");
            return false;
        } else {
            textInputLayoutPassword.setError(null);
            textInputLayoutPasswordConfirm.setError(null);
            return true;
        }


    }
    public String getPassword(){
        return textInputLayoutPassword.getEditText().getText().toString();
    }
}

package com.example.fellow_traveller.Register_Feature;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage2Fragment extends Fragment {

    private TextInputLayout textInputLayout_pass_1, textInputLayout_pass_2;
    private View view;
    private String pass_1 = "aD@ffff1";
    private String pass_2 = "aD@ffff1";


    public RegisterStage2Fragment() {
        // Required empty public constructor
        Log.i("Lifecycle", "contractor ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Lifecycle", "onCreateView");

        view = inflater.inflate(R.layout.fragment_register_stage_2, container, false);
        textInputLayout_pass_1 = view.findViewById(R.id.RegisterStage2Fragment_textInputLayout_password_1);
        textInputLayout_pass_2 = view.findViewById(R.id.RegisterStage2Fragment_textInputLayout_password_2);
        textInputLayout_pass_1.getEditText().setText(pass_1);
        textInputLayout_pass_2.getEditText().setText(pass_2);
        // Log.i("Fragment2","RegisterStage2Fragment");
        return view;
    }

    @Override
    public void onPause() {
        //textInputLayout_pass_1.getEditText().setText("pojog0jrgre");

        // Log.i("Lifecycle", "onPause");
        super.onPause();
    }

    @Override
    public void onStart() {
        //textInputLayout_pass_1.getEditText().setText("pojog0jrgre");

        //Log.i("Lifecycle", "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        //textInputLayout_pass_1.getEditText().setText("pojog0jrgre");

        //Log.i("Lifecycle", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("textInputLayout_pass_1", "onDestroy");
        pass_1 = textInputLayout_pass_1.getEditText().getText().toString();
        pass_2 = textInputLayout_pass_2.getEditText().getText().toString();
        //textInputLayout_pass_1.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        //textInputLayout_pass_2.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage2Fragment";
    }

    public int getRank() {
        return 2;
    }

    public Boolean isOk() {
        String password = textInputLayout_pass_1.getEditText().getText().toString();
        String password_1 = textInputLayout_pass_2.getEditText().getText().toString();

        if (!isTextValid(password) || password.length() < 6) {
            textInputLayout_pass_1.setError("Password is not valid");
            return false;
        } else if (!password.equals(password_1)) {
            textInputLayout_pass_1.setError("The passwords is not the same");
            textInputLayout_pass_2.setError("The passwords is not the same");
            return false;
        } else {
            textInputLayout_pass_1.setError(null);
            textInputLayout_pass_2.setError(null);
            return true;
        }


    }

    public boolean isTextValid(String textToCheck) {
        Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]).+$");
        return textPattern.matcher(textToCheck).matches();
    }

}

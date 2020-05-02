package com.example.fellow_traveller.Register;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

import static com.example.fellow_traveller.Util.InputValidation.isValidEmail;

public class RegisterStage1Fragment extends Fragment {

    private TextInputLayout textInputLayoutEmail;
    private GlobalClass globalClass;
    private View view;

    public RegisterStage1Fragment() {

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

package com.example.fellow_traveller.Register;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterStage1Fragment extends Fragment {

    private TextInputLayout textInputLayout;
    //private TextInputEditText editText;
    private String mail = "";

    private View view;

    public RegisterStage1Fragment() {
        // Required empty public constructor
        //Log.i("Fragment","contractor RegisterStage1Fragment");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_stage_1, container, false);
        // editText = view.findViewById(R.id.RegisterStage1Fragment_editText_email);
        textInputLayout = view.findViewById(R.id.RegisterStage1Fragment_textInputLayout_email);
        Log.i("Fragment", "RegisterStage1Fragment");

        return view;
    }

    public String toString() {
        return "RegisterStage1Fragment";
    }

    public int getRank() {
        return 1;
    }

    public Boolean isOk() {
        //Log.i("isOk",textInputLayout.getEditText().getText().length()+"");
        if (textInputLayout.getEditText().getText().length() < 1) {
            textInputLayout.setError("Please check the email field !");
            return false;
        } else {
            Boolean res = isValidEmail(textInputLayout.getEditText().getText());
            if (res) {
                textInputLayout.setError(null);
                return true;

            } else {
                textInputLayout.setError("The email is incorrect !");
                return false;
            }
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public String GetEmail(){
        return textInputLayout.getEditText().getText().toString();
    }

}

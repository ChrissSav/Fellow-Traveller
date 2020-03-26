package com.example.fellow_traveller.Register_Feature;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage4Fragment extends Fragment {

    private View view;
    private TextInputLayout textInputLayout_birthday;
    private String birthday = "";

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


    public int getRank() {
        return 4;
    }

}

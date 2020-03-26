package com.example.fellow_traveller.Register_Feature;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage3Fragment extends Fragment {

    private View view;
    private TextInputLayout textInputLayout_name, textInputLayout_surname;
    private String name = "Sypros";
    private String surname = "Rantoglou";

    public RegisterStage3Fragment() {
        // Required empty public constructor
        //Log.i("Lifecycle_Stage3", "contractor ");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Log.i("dd", "RegisterStage1Fragment");
       // Log.i("Lifecycle_Stage3", "onCreateView ");
        view = inflater.inflate(R.layout.fragment_register_stage_3, container, false);

        textInputLayout_name = view.findViewById(R.id.RegisterStage3Fragment_textInputLayout_name);
        textInputLayout_surname = view.findViewById(R.id.RegisterStage3Fragment_textInputLayout_surname);
        textInputLayout_name.getEditText().setText(name);
        textInputLayout_surname.getEditText().setText(surname);


        return view;
    }

    @Override
    public void onDestroy() {
        //Log.i("Lifecycle_Stage3", "onDestroy ");

        // Log.i("Lifecycle", "onDestroy");
        name = textInputLayout_name.getEditText().getText().toString();
        surname = textInputLayout_surname.getEditText().getText().toString();
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage3Fragment";
    }

    public int getRank() {
        return 3;
    }

    public Boolean isOk() {
        String name_1 = textInputLayout_name.getEditText().getText().toString();
        String surname_1 = textInputLayout_surname.getEditText().getText().toString();

        if (name_1.length() < 3) {
            textInputLayout_name.setError("Not Valid");
            return false;
        } else if (surname_1.length() < 3) {
            textInputLayout_surname.setError("Not Valid");
            return false;
        } else {
            textInputLayout_name.setError(null);
            textInputLayout_surname.setError(null);
            return true;
        }


    }

}

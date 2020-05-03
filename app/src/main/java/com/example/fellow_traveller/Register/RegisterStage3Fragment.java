package com.example.fellow_traveller.Register;


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
public class RegisterStage3Fragment extends Fragment {

    private View view;
    private TextInputLayout textInputLayoutName, textInputLayoutSurname;
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
        view = inflater.inflate(R.layout.fragment_user_register_stage_3, container, false);

        textInputLayoutName = view.findViewById(R.id.RegisterStage3Fragment_textInputLayout_name);
        textInputLayoutSurname = view.findViewById(R.id.RegisterStage3Fragment_textInputLayout_surname);
        textInputLayoutName.getEditText().setText(name);
        textInputLayoutSurname.getEditText().setText(surname);


        return view;
    }

    @Override
    public void onDestroy() {

        name = textInputLayoutName.getEditText().getText().toString();
        surname = textInputLayoutSurname.getEditText().getText().toString();
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage3Fragment";
    }

    public int getRank() {
        return 4;
    }

    public Boolean validateFragment() {
        String name_1 = textInputLayoutName.getEditText().getText().toString();
        String surname_1 = textInputLayoutSurname.getEditText().getText().toString();

        if (name_1.length() < 3) {
            textInputLayoutName.setError("Not Valid");
            return false;
        } else if (surname_1.length() < 3) {
            textInputLayoutSurname.setError("Not Valid");
            return false;
        } else {
            textInputLayoutName.setError(null);
            textInputLayoutSurname.setError(null);
            return true;
        }


    }

    public String getFirstName(){
        return textInputLayoutName.getEditText().getText().toString();
    }

    public String getLastName(){
        return textInputLayoutSurname.getEditText().getText().toString();
    }

}

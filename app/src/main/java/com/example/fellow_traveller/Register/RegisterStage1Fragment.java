package com.example.fellow_traveller.Register;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterStage1Fragment extends Fragment {

    private GlobalClass globalClass;
    private View view;
    private EditText emailEditText;
    private ImageButton eraseButton;
    private View underlineEmail;
    private boolean emailFlag = false;

    public RegisterStage1Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        view = inflater.inflate(R.layout.fragment_user_register_stage_1, container, false);
        emailEditText = view.findViewById(R.id.FragmentRegisterStage1_email_editText);
        eraseButton = view.findViewById(R.id.FragmentRegisterStage1_email_eraseButton);
        underlineEmail = view.findViewById(R.id.FragmentRegisterStage1_email_underline);

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getActivity(), "Get Focus", Toast.LENGTH_SHORT).show();
                    underlineEmail.setPressed(true);
                    emailFlag = true;
                }else{
                    //Toast.makeText(getActivity(), "Lost Focus", Toast.LENGTH_SHORT).show();
                    underlineEmail.setPressed(false);
                    emailFlag = false;
                }

            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    eraseButton.setVisibility(View.VISIBLE);
                else
                    eraseButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditText.setText("");
            }
        });




        return view;
    }

    public String toString() {
        return "RegisterStage1Fragment";
    }

    public int getRank() {
        return 1;
    }




    public String getEmail() {
        return emailEditText.getText().toString();
    }


    public EditText getEditText() {
        return emailEditText;
    }

    public void setErrorToEditText(String msg) {
        emailEditText.setError(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(emailFlag)
            underlineEmail.setPressed(true);
    }
}

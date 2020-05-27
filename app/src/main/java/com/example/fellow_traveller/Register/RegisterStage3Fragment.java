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
import android.widget.Toast;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage3Fragment extends Fragment {

    private View view;
    private EditText editTextName, editTextSurname;
    private String name = "Sypros";
    private String surname = "Rantoglou";
    private View underlineName, underlineSurname;
    private boolean nameFlag = false, surnameFlag = false;
    private ImageButton nameEraseButton, surNameEraseButton;

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

        editTextName = view.findViewById(R.id.FragmentRegisterStage3_name_editText);
        editTextSurname = view.findViewById(R.id.FragmentRegisterStage3_surname_editText);
        underlineName = view.findViewById(R.id.FragmentRegisterStage3_name_underline);
        underlineSurname = view.findViewById(R.id.FragmentRegisterStage3_surname_underline);
        nameEraseButton = view.findViewById(R.id.FragmentRegisterStage3_name_erase_button);
        surNameEraseButton = view.findViewById(R.id.FragmentRegisterStage3_surname_erase_button);
        //textInputLayoutName.getEditText().setText(name);
        //textInputLayoutSurname.getEditText().setText(surname);


        //Control the focus of editexts for custom apperance
        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getActivity(), "Get Focus", Toast.LENGTH_SHORT).show();
                    underlineName.setPressed(true);
                    nameFlag = true;
                }else{
                    //Toast.makeText(getActivity(), "Lost Focus", Toast.LENGTH_SHORT).show();
                    underlineName.setPressed(false);
                    nameFlag = false;
                }

            }
        });

        editTextSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getActivity(), "Get Focus", Toast.LENGTH_SHORT).show();
                    underlineSurname.setPressed(true);
                    surnameFlag = true;
                }else{
                    //Toast.makeText(getActivity(), "Lost Focus", Toast.LENGTH_SHORT).show();
                    underlineSurname.setPressed(false);
                    surnameFlag = false;
                }

            }
        });


        //Add text watchers for edit text to make custom erasers
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    nameEraseButton.setVisibility(View.VISIBLE);
                else
                    nameEraseButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    surNameEraseButton.setVisibility(View.VISIBLE);
                else
                    surNameEraseButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //Erase any text from editText when they clicked
        nameEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setText("");
            }
        });
        surNameEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSurname.setText("");
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {

        name = editTextName.getText().toString();
        surname = editTextSurname.getText().toString();
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage3Fragment";
    }

    public int getRank() {
        return 3;
    }

    public Boolean validateFragment() {
        String name_1 = editTextName.getText().toString();
        String surname_1 = editTextSurname.getText().toString();

        if (name_1.length() < 3) {
            editTextName.setError("Not Valid");
            return false;
        } else if (surname_1.length() < 3) {
            editTextSurname.setError("Not Valid");
            return false;
        } else {
            editTextName.setError(null);
            editTextSurname.setError(null);
            return true;
        }


    }

    public String getFirstName(){
        return editTextName.getText().toString();
    }

    public String getLastName(){
        return editTextSurname.getText().toString();
    }



    @Override
    public void onResume() {
        super.onResume();
        if(nameFlag)
            underlineName.setPressed(true);
        else if(surnameFlag)
            underlineSurname.setPressed(true);
    }
}

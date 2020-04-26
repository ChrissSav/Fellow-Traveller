package com.example.fellow_traveller.Register;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import static com.example.fellow_traveller.Util.InputValidation.isValidPassword;


public class RegisterStage2Fragment extends Fragment {
    private TextInputLayout textInputLayoutPassword, textInputLayoutConfirmPassword;
    private View view;

    private String tempPassword = "aD@ffff1";
    private String tempConfirmPassword = "aD@ffff1";


    public RegisterStage2Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register_stage_2, container, false);
        textInputLayoutPassword = view.findViewById(R.id.RegisterStage2Fragment_textInputLayout_password_1);
        textInputLayoutConfirmPassword = view.findViewById(R.id.RegisterStage2Fragment_textInputLayout_password_2);
        textInputLayoutPassword.getEditText().setText(tempPassword);
        textInputLayoutConfirmPassword.getEditText().setText(tempConfirmPassword);
        return view;
    }

    @Override
    public void onDestroy() {
        tempPassword = textInputLayoutPassword.getEditText().getText().toString();
        tempConfirmPassword = textInputLayoutConfirmPassword.getEditText().getText().toString();
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
        String confirmPassword = textInputLayoutConfirmPassword.getEditText().getText().toString();

        // TODO check password complexity as user types in the input field.
        // TODO isValidPassword returns an array resource identifiers, access them by getResources().getString(ResID)
        // TODO remember to include the first message below manually as it is not returned by the method itself
        // TODO R.string.PASSWORD_COMPLEXITY_SHOULD_CONTAIN

        ArrayList<Integer> errors = isValidPassword(password);

        if (!errors.isEmpty()) {
            textInputLayoutPassword.setError(getContext().getString(R.string.PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
            textInputLayoutConfirmPassword.setError(null);

            // TODO this is for debug only, to show how the isValidPassword() works
            Toast.makeText(getContext(), getResources().getString(R.string.PASSWORD_COMPLEXITY_SHOULD_CONTAIN), Toast.LENGTH_SHORT).show();
            for (Integer error : errors) {
                // TODO implement how you will display the errors to the user.
                // TODO remove debug output
                Toast.makeText(getContext(), getResources().getString(error), Toast.LENGTH_SHORT).show();
            }
            return false;
        } else if (!password.equals(confirmPassword)) {
            textInputLayoutPassword.setError(null);
            textInputLayoutConfirmPassword.setError(getContext().getString(R.string.PASSWORD_DO_NOT_MATCH));
            return false;
        } else {
            textInputLayoutPassword.setError(null);
            textInputLayoutConfirmPassword.setError(null);
            return true;
        }


    }

    public String getPassword() {
        return textInputLayoutPassword.getEditText().getText().toString();
    }
}

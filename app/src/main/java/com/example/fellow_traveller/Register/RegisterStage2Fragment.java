package com.example.fellow_traveller.Register;


import android.os.Bundle;
import android.text.InputType;
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
    private TextInputLayout passwordTextInputLayout, confirmPasswordTextInputLayout;
    private View view;

    // TODO remove and replace with proper placeholder
    private String tempPassword = "aD@ffff1";
    private String tempConfirmPassword = "aD@ffff1";


    public RegisterStage2Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_register_stage2, container, false);
        passwordTextInputLayout = view.findViewById(R.id.fragment_user_register_stage2_password_textInputLayout);
        confirmPasswordTextInputLayout = view.findViewById(R.id.fragment_user_register_stage2_confirmPassword_textInputLayout);
        passwordTextInputLayout.getEditText().setText(tempPassword);
        confirmPasswordTextInputLayout.getEditText().setText(tempConfirmPassword);

        passwordTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gets current visibility of the input field
                int oldPasswordEditTextVisibility = passwordTextInputLayout.getEditText().getInputType();

                // indicates whether text on the password field is either hidden or visible
                int passwordVisible = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                int passwordHidden = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

                if (oldPasswordEditTextVisibility == passwordVisible) {
                    // hide all input
                    passwordTextInputLayout.getEditText().setInputType(passwordHidden);
                    confirmPasswordTextInputLayout.getEditText().setInputType(passwordHidden);
                } else if (oldPasswordEditTextVisibility == passwordHidden) {
                    // make all input visible
                    passwordTextInputLayout.getEditText().setInputType(passwordVisible);
                    confirmPasswordTextInputLayout.getEditText().setInputType(passwordVisible);
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        tempPassword = passwordTextInputLayout.getEditText().getText().toString();
        tempConfirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString();
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage2Fragment";
    }

    public int getRank() {
        return 2;
    }

    public Boolean validateFragment() {
        String password = passwordTextInputLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString();

        // TODO check password complexity as user types in the input field.
        // TODO isValidPassword returns an array resource identifiers, access them by getResources().getString(ResID)
        // TODO remember to include the first message below manually as it is not returned by the method itself
        // TODO see R.string.PASSWORD_COMPLEXITY_SHOULD_CONTAIN

        ArrayList<Integer> errors = isValidPassword(password);

        if (!errors.isEmpty()) {
            passwordTextInputLayout.setError(getContext().getString(R.string.ERROR_PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
            confirmPasswordTextInputLayout.setError(null);

            // TODO this is for debug only, to show how the isValidPassword() works
            Toast.makeText(getContext(), getResources().getString(R.string.ERROR_PASSWORD_COMPLEXITY_SHOULD_CONTAIN), Toast.LENGTH_SHORT).show();
            for (Integer error : errors) {
                // TODO implement how you will display the errors to the user.
                // TODO remove debug output
                Toast.makeText(getContext(), getResources().getString(error), Toast.LENGTH_SHORT).show();
            }
            return false;
        } else if (!password.equals(confirmPassword)) {
            passwordTextInputLayout.setError(null);
            confirmPasswordTextInputLayout.setError(getContext().getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH));
            return false;
        } else {
            passwordTextInputLayout.setError(null);
            confirmPasswordTextInputLayout.setError(null);
            return true;
        }


    }

    public String getPassword() {
        return passwordTextInputLayout.getEditText().getText().toString();
    }
}

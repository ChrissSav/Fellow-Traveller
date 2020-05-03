package com.example.fellow_traveller.Register;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.fellow_traveller.Util.InputValidation.validatePasswordComplexity;


public class RegisterStage2Fragment extends Fragment {
    private View view;
    private TextInputLayout passwordTextInputLayout, confirmPasswordTextInputLayout;
    private ArrayList<TextView> passwordComplexityRequirementsTextViews;

    // TODO remove and replace with proper placeholder
    private String tempPassword = "aD@ffff1";
    private String tempConfirmPassword = "aD@ffff1";


    public RegisterStage2Fragment() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_register_stage_2, container, false);
        passwordTextInputLayout = view.findViewById(R.id.fragment_user_register_stage2_password_textInputLayout);
        confirmPasswordTextInputLayout = view.findViewById(R.id.fragment_user_register_stage2_confirmPassword_textInputLayout);
        Objects.requireNonNull(passwordTextInputLayout.getEditText()).setText(tempPassword);
        Objects.requireNonNull(confirmPasswordTextInputLayout.getEditText()).setText(tempConfirmPassword);

        passwordComplexityRequirementsTextViews = new ArrayList<>();
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_digit_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_lowercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_uppercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_special_char_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_min_length_TextView));

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

        Objects.requireNonNull(passwordTextInputLayout.getEditText()).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePasswordComplexity(s.toString(), passwordComplexityRequirementsTextViews);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Objects.requireNonNull(passwordTextInputLayout.getEditText()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passwordTextInputLayout.setError(null);
                return false;
            }
        });

        Objects.requireNonNull(confirmPasswordTextInputLayout.getEditText()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirmPasswordTextInputLayout.setError(null);
                return false;
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
        return 3;
    }

    public Boolean validateFragment() {
        String password = passwordTextInputLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString();
        Boolean isValidFragment;

        if (password.isEmpty()) {
            passwordTextInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            isValidFragment = false;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordTextInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            isValidFragment = false;
        } else if (!validatePasswordComplexity(password, passwordComplexityRequirementsTextViews)) {
            passwordTextInputLayout.setError(getContext().getString(R.string.ERROR_PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
            confirmPasswordTextInputLayout.setError(null);
            isValidFragment = false;
        } else if (!password.equals(confirmPassword)) {
            passwordTextInputLayout.setError(null);
            confirmPasswordTextInputLayout.setError(getContext().getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH));
            isValidFragment = false;
        } else {
            passwordTextInputLayout.setError(null);
            confirmPasswordTextInputLayout.setError(null);
            isValidFragment = true;
        }

        return isValidFragment;

    }

    public String getPassword() {
        return passwordTextInputLayout.getEditText().getText().toString();
    }
}

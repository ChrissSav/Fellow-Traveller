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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fellow_traveller.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.fellow_traveller.Util.InputValidation.validatePasswordComplexity;


public class RegisterStage2Fragment extends Fragment {
    private View view;
    private EditText passwordEditText, confirmPasswordEditText;
    private ArrayList<TextView> passwordComplexityRequirementsTextViews;
    private View underlinePassword, underlineConfirmPassword;
    private Button passwordShowButton, confirmPasswordShowButton;
    private boolean passwordFlag = false, confirmPasswordFlag = false, passwordHidden = true, confirmPasswordHidden = true;

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
        passwordEditText = view.findViewById(R.id.FragmentRegisterStage2_password_edit_text);
        confirmPasswordEditText = view.findViewById(R.id.FragmentRegisterStage2_password2_edit_text);
        underlinePassword = view.findViewById(R.id.FragmentRegisterStage1_password_underline);
        underlineConfirmPassword = view.findViewById(R.id.FragmentRegisterStage1_password2_underline);
        passwordShowButton = view.findViewById(R.id.FragmentRegisterStage2_display_password_button);
        confirmPasswordShowButton = view.findViewById(R.id.FragmentRegisterStage2_display_password2_button);

//        Objects.requireNonNull(passwordEditText).setText(tempPassword);
//        Objects.requireNonNull(confirmPasswordEditText).setText(tempConfirmPassword);

        passwordComplexityRequirementsTextViews = new ArrayList<>();
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_digit_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_lowercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_uppercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_special_char_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) view.findViewById(R.id.fragment_user_register_password_complexity_min_length_TextView));

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getActivity(), "Get Focus", Toast.LENGTH_SHORT).show();
                    underlinePassword.setPressed(true);
                    passwordFlag = true;
                }else{
                    //Toast.makeText(getActivity(), "Lost Focus", Toast.LENGTH_SHORT).show();
                    underlinePassword.setPressed(false);
                    passwordFlag = false;
                }

            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getActivity(), "Get Focus", Toast.LENGTH_SHORT).show();
                    underlineConfirmPassword.setPressed(true);
                    confirmPasswordFlag = true;
                }else{
                    //Toast.makeText(getActivity(), "Lost Focus", Toast.LENGTH_SHORT).show();
                    underlineConfirmPassword.setPressed(false);
                    confirmPasswordFlag = false;
                }

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePasswordComplexity(charSequence.toString(), passwordComplexityRequirementsTextViews);
                if (!charSequence.toString().trim().isEmpty())
                    passwordShowButton.setVisibility(View.VISIBLE);
                else
                    passwordShowButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    confirmPasswordShowButton.setVisibility(View.VISIBLE);
                else
                    confirmPasswordShowButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordHidden) {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.length());
                    passwordHidden = false;
                }else{
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.length());
                    passwordHidden = true;
                }

            }
        });

        confirmPasswordShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                if(confirmPasswordHidden){
                    confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmPasswordEditText.setSelection(confirmPasswordEditText.length());
                    confirmPasswordFlag = false;
                }
                else{
                    confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPasswordEditText.setSelection(confirmPasswordEditText.length());
                    confirmPasswordFlag = true;
                }

            }
        });

//        passwordEditText.setEndIconOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // gets current visibility of the input field
//                int oldPasswordEditTextVisibility = passwordEditText.getInputType();
//
//                // indicates whether text on the password field is either hidden or visible
//                int passwordVisible = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
//                int passwordHidden = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
//
//                if (oldPasswordEditTextVisibility == passwordVisible) {
//                    // hide all input
//                    passwordEditText.setInputType(passwordHidden);
//                    confirmPasswordEditText.setInputType(passwordHidden);
//                } else if (oldPasswordEditTextVisibility == passwordHidden) {
//                    // make all input visible
//                    passwordEditText.setInputType(passwordVisible);
//                    confirmPasswordEditText.setInputType(passwordVisible);
//                }
//            }
//        });
//
//        Objects.requireNonNull(passwordEditText).addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                validatePasswordComplexity(s.toString(), passwordComplexityRequirementsTextViews);
//                if (!s.toString().trim().isEmpty())
//                    passwordShowButton.setVisibility(View.VISIBLE);
//                else
//                    passwordShowButton.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        Objects.requireNonNull(passwordEditText).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passwordEditText.setError(null);
                return false;
            }
        });

        Objects.requireNonNull(confirmPasswordEditText).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirmPasswordEditText.setError(null);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        tempPassword = passwordEditText.getText().toString();
        tempConfirmPassword = confirmPasswordEditText.getText().toString();
        super.onDestroy();
    }

    public String toString() {
        return "RegisterStage2Fragment";
    }

    public int getRank() {
        return 2;
    }

    public Boolean validateFragment() {
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        Boolean isValidFragment;

        if (password.isEmpty()) {
            passwordEditText.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            isValidFragment = false;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            isValidFragment = false;
        } else if (!validatePasswordComplexity(password, passwordComplexityRequirementsTextViews)) {
            passwordEditText.setError(getContext().getString(R.string.ERROR_PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
            confirmPasswordEditText.setError(null);
            isValidFragment = false;
        } else if (!password.equals(confirmPassword)) {
            passwordEditText.setError(null);
            confirmPasswordEditText.setError(getContext().getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH));
            isValidFragment = false;
        } else {
            passwordEditText.setError(null);
            confirmPasswordEditText.setError(null);
            isValidFragment = true;
        }

        return isValidFragment;

    }

    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(passwordFlag)
            underlinePassword.setPressed(true);
        else if(confirmPasswordFlag)
            underlineConfirmPassword.setPressed(true);
    }
}

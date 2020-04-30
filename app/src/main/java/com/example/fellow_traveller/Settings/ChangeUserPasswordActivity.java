package com.example.fellow_traveller.Settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserChangePasswordModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.fellow_traveller.Util.InputValidation.validatePasswordComplexity;

public class ChangeUserPasswordActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button changePasswordButton;
    private TextInputLayout oldPasswordInputLayout;
    private TextInputLayout newPasswordInputLayout;
    private TextInputLayout confirmNewPasswordInputLayout;
    private ArrayList<TextView> passwordComplexityRequirementsTextViews;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backButton = findViewById(R.id.changePassord_back_button);
        changePasswordButton = findViewById(R.id.changePassword_Button);

        oldPasswordInputLayout = findViewById(R.id.ChangePassword_editText_old_password);
        newPasswordInputLayout = findViewById(R.id.ChangePassword_editText_new_password);
        confirmNewPasswordInputLayout = findViewById(R.id.ChangePassword_editText_repeat_password);

        passwordComplexityRequirementsTextViews = new ArrayList<>();
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_digit_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_lowercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_uppercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_special_char_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_min_length_TextView));


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String oldPassword = oldPasswordInputLayout.getEditText().getText().toString();
                String newPassword = newPasswordInputLayout.getEditText().getText().toString();
                String confirmNewPassword = confirmNewPasswordInputLayout.getEditText().getText().toString();
                GlobalClass context = (GlobalClass) getApplicationContext();

                // remove any previous errors
                oldPasswordInputLayout.setError(null);
                newPasswordInputLayout.setError(null);
                confirmNewPasswordInputLayout.setError(null);

                // check for empty input fields
                if (oldPassword.isEmpty()) {
                    oldPasswordInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                }
                if (newPassword.isEmpty()) {
                    newPasswordInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                }
                if (confirmNewPassword.isEmpty()) {
                    confirmNewPasswordInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                } else {
                    // see if there's any errors
                    if (!validatePasswordComplexity(newPassword, passwordComplexityRequirementsTextViews)) {
                        newPasswordInputLayout.setError(getResources().getString(R.string.ERROR_PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
                        confirmNewPasswordInputLayout.setError(null);
                    } else if (!newPassword.equals(confirmNewPassword)) {
                        newPasswordInputLayout.setError(null);
                        confirmNewPasswordInputLayout.setError(getResources().getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH));
                    } else {
                        newPasswordInputLayout.setError(null);
                        confirmNewPasswordInputLayout.setError(null);

                        if (!oldPassword.equals(newPassword)) {
                            // TODO check if user object is null before calling the client API, maybe try-catch
                            // Create user object from model
                            UserChangePasswordModel user = new UserChangePasswordModel(oldPassword, newPassword);
                            new FellowTravellerAPI(context).userChangePassword(Objects.requireNonNull(user), new StatusCallBack() {
                                @Override
                                public void onSuccess(String status) {
                                    // TODO password was changed successfully
                                    Toast.makeText(v.getContext(), getResources().getString(R.string.PASSWORD_CHANGED_SUCCESSFULLY), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String errorMsg) {
                                    oldPasswordInputLayout.setError(getResources().getString(R.string.ERROR_PASSWORD_CHANGE_INVALID_CURRENT_PASSWORD));
                                }
                            });
                        } else {
                            newPasswordInputLayout.setError(getResources().getString(R.string.ERROR_OLD_PASSWORD_CANNOT_BE_SAME_AS_NEW_PASSWORD));
                        }
                    }

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Clear any error on the input layouts once user clicks on them
        Objects.requireNonNull(oldPasswordInputLayout.getEditText()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                oldPasswordInputLayout.setError(null);
                return false;
            }
        });

        Objects.requireNonNull(newPasswordInputLayout.getEditText()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                newPasswordInputLayout.setError(null);
                return false;
            }
        });

        Objects.requireNonNull(confirmNewPasswordInputLayout.getEditText()).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                confirmNewPasswordInputLayout.setError(null);
                return false;
            }
        });

        oldPasswordInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gets current visibility of the input field
                int oldPasswordEditTextVisibility = oldPasswordInputLayout.getEditText().getInputType();

                // indicates whether text on the password field is visible or hidden
                int passwordVisible = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                int passwordHidden = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

                if (oldPasswordEditTextVisibility == passwordVisible) {
                    // hide all input
                    oldPasswordInputLayout.getEditText().setInputType(passwordHidden);
                    newPasswordInputLayout.getEditText().setInputType(passwordHidden);
                    confirmNewPasswordInputLayout.getEditText().setInputType(passwordHidden);
                } else if (oldPasswordEditTextVisibility == passwordHidden) {
                    // make all input visible
                    oldPasswordInputLayout.getEditText().setInputType(passwordVisible);
                    newPasswordInputLayout.getEditText().setInputType(passwordVisible);
                    confirmNewPasswordInputLayout.getEditText().setInputType(passwordVisible);
                }
            }
        });

        Objects.requireNonNull(newPasswordInputLayout.getEditText()).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // clear any previous errors for the input layouts
                newPasswordInputLayout.setError(null);
                confirmNewPasswordInputLayout.setError(null);
                validatePasswordComplexity(s.toString(), passwordComplexityRequirementsTextViews);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
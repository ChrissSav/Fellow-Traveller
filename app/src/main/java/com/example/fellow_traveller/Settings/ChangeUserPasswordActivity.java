package com.example.fellow_traveller.Settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserChangePasswordModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.fellow_traveller.Util.InputValidation.validatePasswordComplexity;

public class ChangeUserPasswordActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button changePasswordButton;
    private EditText oldPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private View underlineOldPassword, underlineNewPassword, underlineConfirmPassword;
    private Button oldPasswordShowButton, newPasswordShowButton, confirmPasswordShowButton;
    private boolean oldPasswordFlag = false, newPasswordFlag = false, confirmPasswordFlag = false;
    private boolean oldPasswordHidden = true, newPasswordHidden = true, confirmPasswordHidden = true;
    private ArrayList<TextView> passwordComplexityRequirementsTextViews;
    private ProgressBar changePasswordProgressBar;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backButton = findViewById(R.id.ActivityChangePassword_back_button);
        changePasswordButton = findViewById(R.id.ActivityChangePassword_change_button);

        oldPasswordEditText = findViewById(R.id.ActivityChangePassword_old_password_edit_text);
        newPasswordEditText = findViewById(R.id.ActivityChangePassword_new_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.ActivityChangePassword_new_password2_edit_text);
        underlineOldPassword = findViewById(R.id.ActivityChangePassword_old_password_underline);
        underlineNewPassword = findViewById(R.id.ActivityChangePassword_new_password_underline);
        underlineConfirmPassword = findViewById(R.id.ActivityChangePassword_new_password2_underline);
        oldPasswordShowButton = findViewById(R.id.ActivityChangePassword_old_password_button);
        newPasswordShowButton = findViewById(R.id.ActivityChangePassword_new_password_button);
        confirmPasswordShowButton = findViewById(R.id.ActivityChangePassword_new_password2_button);
        changePasswordProgressBar = findViewById(R.id.ActivityChangePassword_progress_bar);

        passwordComplexityRequirementsTextViews = new ArrayList<>();
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_digit_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_lowercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_uppercase_letter_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_special_char_TextView));
        passwordComplexityRequirementsTextViews.add((TextView) findViewById(R.id.activity_change_password_password_complexity_min_length_TextView));

        //<-------------- Set focus to EdiTexts ----------------->
        oldPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    underlineOldPassword.setPressed(true);
                    oldPasswordFlag = true;
                }
                else{
                    underlineOldPassword.setPressed(false);
                    oldPasswordFlag = false;
                }
            }
        });
        newPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    underlineNewPassword.setPressed(true);
                    newPasswordFlag = true;
                }
                else{
                    underlineNewPassword.setPressed(false);
                    newPasswordFlag = false;
                }
            }
        });
        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    underlineConfirmPassword.setPressed(true);
                    confirmPasswordFlag = true;
                }
                else{
                    underlineConfirmPassword.setPressed(false);
                    confirmPasswordFlag = false;
                }
            }
        });
        //<------------------------------------------>


        //<---------------- Make visible the show password buttons if they are not empty -------------------->
        oldPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    oldPasswordShowButton.setVisibility(View.VISIBLE);
                else
                    oldPasswordShowButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        newPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    newPasswordShowButton.setVisibility(View.VISIBLE);
                else
                    newPasswordShowButton.setVisibility(View.GONE);
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

        //<----------------------------------------------------------------------->


        //<---------------------- Button Listeners ----------------------->

        oldPasswordShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldPasswordHidden) {
                    oldPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    oldPasswordEditText.setSelection(oldPasswordEditText.length());
                    oldPasswordHidden = false;
                }else{
                    oldPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    oldPasswordEditText.setSelection(oldPasswordEditText.length());
                    oldPasswordHidden = true;
                }
            }
        });

        newPasswordShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newPasswordHidden) {
                    newPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    newPasswordEditText.setSelection(newPasswordEditText.length());
                    newPasswordHidden = false;
                }else{
                    newPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newPasswordEditText.setSelection(newPasswordEditText.length());
                    newPasswordHidden = true;
                }
            }
        });
        confirmPasswordShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmPasswordHidden){
                    confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmPasswordEditText.setSelection(confirmPasswordEditText.length());
                    confirmPasswordHidden = false;
                }
                else{
                    confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPasswordEditText.setSelection(confirmPasswordEditText.length());
                    confirmPasswordHidden = true;
                }
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmPasswordEditText.getText().toString();
                GlobalClass context = (GlobalClass) getApplicationContext();

                // remove any previous errors
                oldPasswordEditText.setError(null);
                newPasswordEditText.setError(null);
                confirmPasswordEditText.setError(null);

                // check for empty input fields
                if (oldPassword.isEmpty()) {
                    oldPasswordEditText.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                }
                if (newPassword.isEmpty()) {
                    newPasswordEditText.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                }
                if (confirmNewPassword.isEmpty()) {
                    confirmPasswordEditText.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                } else {
                    // see if there's any errors
                    if (!validatePasswordComplexity(newPassword, passwordComplexityRequirementsTextViews)) {
                        newPasswordEditText.setError(getResources().getString(R.string.ERROR_PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
                        confirmPasswordEditText.setError(null);
                    } else if (!newPassword.equals(confirmNewPassword)) {
                        newPasswordEditText.setError(null);
                        confirmPasswordEditText.setError(getResources().getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH));
                    } else {
                        newPasswordEditText.setError(null);
                        confirmPasswordEditText.setError(null);

                        if (!oldPassword.equals(newPassword)) {
                            // TODO check if user object is null before calling the client API, maybe try-catch

                            changePasswordProgressBar.setVisibility(View.VISIBLE);

                            // Create user object from model
                            UserChangePasswordModel user = new UserChangePasswordModel(oldPassword, newPassword);
                            new FellowTravellerAPI(context).userChangePassword(Objects.requireNonNull(user), new StatusCallBack() {
                                @Override
                                public void onSuccess(String status) {
                                    // TODO password was changed successfully
                                    Toast.makeText(v.getContext(), getResources().getString(R.string.PASSWORD_CHANGED_SUCCESSFULLY), Toast.LENGTH_LONG).show();
                                    changePasswordProgressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(String errorMsg) {
                                    oldPasswordEditText.setError(getResources().getString(R.string.ERROR_PASSWORD_CHANGE_INVALID_CURRENT_PASSWORD));
                                    changePasswordProgressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            newPasswordEditText.setError(getResources().getString(R.string.ERROR_OLD_PASSWORD_CANNOT_BE_SAME_AS_NEW_PASSWORD));
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
        Objects.requireNonNull(oldPasswordEditText).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                oldPasswordEditText.setError(null);
                return false;
            }
        });

        Objects.requireNonNull(newPasswordEditText).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                newPasswordEditText.setError(null);
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



        Objects.requireNonNull(newPasswordEditText).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // clear any previous errors for the input layouts
                newPasswordEditText.setError(null);
                confirmPasswordEditText.setError(null);
                validatePasswordComplexity(s.toString(), passwordComplexityRequirementsTextViews);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check when we resume if we had focus on an editText
        if(oldPasswordFlag)
            underlineOldPassword.setPressed(true);
        else if (newPasswordFlag)
            underlineNewPassword.setPressed(true);
        else if(confirmPasswordFlag)
            underlineConfirmPassword.setPressed(true);
    }
}
package com.example.fellow_traveller.Settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import static com.example.fellow_traveller.Util.InputValidation.isValidPassword;

public class ChangeUserPasswordActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button changePasswordButton;
    private TextInputLayout oldPasswordInputLayout;
    private TextInputLayout newPasswordInputLayout;
    private TextInputLayout confirmNewPasswordInputLayout;

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

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String oldPassword = oldPasswordInputLayout.getEditText().getText().toString();
                String newPassword = newPasswordInputLayout.getEditText().getText().toString();
                String confirmNewPassword = confirmNewPasswordInputLayout.getEditText().getText().toString();
                GlobalClass context = (GlobalClass) getApplicationContext();

                // remove any errors from previous clicks
                oldPasswordInputLayout.setError(null);
                newPasswordInputLayout.setError(null);
                confirmNewPasswordInputLayout.setError(null);

                // check if any of the fields is empty
                if (oldPassword.isEmpty()) {
                    oldPasswordInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                }
                if (newPassword.isEmpty()) {
                    newPasswordInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                }
                if (confirmNewPassword.isEmpty()) {
                    confirmNewPasswordInputLayout.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
                } else {
                    // check the complexity of the new password
                    ArrayList<Integer> errors = isValidPassword(newPassword);

                    // see if there's any errors
                    if (!errors.isEmpty()) {
                        newPasswordInputLayout.setError(getResources().getString(R.string.ERROR_PASSWORD_DOES_NOT_MEET_COMPLEXITY_REQUIREMENTS));
                        confirmNewPasswordInputLayout.setError(null);

                        // TODO remove this debug loop once you implement proper password complexity error handling
                        Toast.makeText(v.getContext(), getResources().getString(R.string.ERROR_PASSWORD_COMPLEXITY_SHOULD_CONTAIN), Toast.LENGTH_SHORT).show();
                        for (Integer error : errors) {
                            // TODO implement how you will display the errors to the user.
                            // TODO remove debug output
                            Toast.makeText(v.getContext(), getResources().getString(error), Toast.LENGTH_SHORT).show();
                        }
                    } else if (!newPassword.equals(confirmNewPassword)) {
                        newPasswordInputLayout.setError(null);
                        confirmNewPasswordInputLayout.setError(getResources().getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH));
                    } else {
                        newPasswordInputLayout.setError(null);
                        confirmNewPasswordInputLayout.setError(null);

                        // Check if old and new passwords are not the same
                        if (!oldPassword.equals(newPassword)) {
                            // TODO check if user object is null before calling the client API, maybe try-catch
                            // Create user object from model
                            UserChangePasswordModel user = new UserChangePasswordModel(oldPassword, newPassword);
                            new FellowTravellerAPI(context).userChangePassword(user, new StatusCallBack() {
                                @Override
                                public void onSuccess(String status) {
                                    // TODO password was changed successfully, note! this fragment's View v was made final on the onClick method.
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

    }
}

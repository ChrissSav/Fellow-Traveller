package com.example.fellow_traveller.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import static com.example.fellow_traveller.Util.InputValidation.isValidPassword;

public class ChangePassword extends AppCompatActivity {

    private ImageButton backButton;
    private Button changePasswordButton;
    private TextInputLayout oldPasswordInputLayout;
    private TextInputLayout newPasswordInputLayout;
    private TextInputLayout confirmNewPasswordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backButton = findViewById(R.id.changePassord_back_button);
        changePasswordButton = findViewById(R.id.changePassword_Button);

        oldPasswordInputLayout = findViewById(R.id.ChangePassword_editText_old_password);
        newPasswordInputLayout = findViewById(R.id.ChangePassword_editText_new_password);
        confirmNewPasswordInputLayout = findViewById(R.id.ChangePassword_editText_repeat_password);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassword = oldPasswordInputLayout.getEditText().getText().toString();
                String newPassword = newPasswordInputLayout.getEditText().getText().toString();
                String confirmNewPassword = confirmNewPasswordInputLayout.getEditText().getText().toString();

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

                        // TODO remove this debug line once you implement proper password complexity error handling
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
                        
                        // new provided password seems okay as far as complexity goes
                        // todo call client API endpoint with the new password and handle everything through the callback
                        // todo new FellowTravellerAPI().changeUserPassword(oldPassword, newPassword, changeUserPasswordCallback(){...});
                    }

                }
            }
        });
    }
}

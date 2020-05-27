package com.example.fellow_traveller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserLoginModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import static com.example.fellow_traveller.Util.InputValidation.isValidEmail;

public class LoginActivity extends AppCompatActivity {


    private Button loginButton, forgotPasswordButton, registerButton, passwordShowButton;
    private GlobalClass globalClass;
    private EditText emailEditText, passwordEditText;
    private View underlineEmail, underlinePassword;
    private boolean emailFlag = false, passwordFlag = false, passwordHidden = true;
    private ImageButton emailEraseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        globalClass = (GlobalClass) getApplicationContext();
        emailEditText = findViewById(R.id.LoginActivity_editText_email);
        passwordEditText = findViewById(R.id.LoginActivity_editText_password);
        underlineEmail = findViewById(R.id.LoginActivity_email_underline);
        underlinePassword = findViewById(R.id.LoginActivity_password_underline);
        emailEraseButton = findViewById(R.id.LoginActivity_email_erase_button);
        passwordShowButton = findViewById(R.id.LoginActivity_display_password_button);
        loginButton = findViewById(R.id.LoginActivity_button_login);
        forgotPasswordButton = findViewById(R.id.LoginActivity_forgot_password_button);
        registerButton = findViewById(R.id.LoginActivity_register_Button);

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    underlineEmail.setPressed(true);
                    emailFlag = true;
                }else{
                    underlineEmail.setPressed(false);
                    emailFlag = false;
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    underlinePassword.setPressed(true);
                    passwordFlag = true;
                }else{
                    underlinePassword.setPressed(false);
                    passwordFlag = false;
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
                    emailEraseButton.setVisibility(View.VISIBLE);
                else
                    emailEraseButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    passwordShowButton.setVisibility(View.VISIBLE);
                else
                    passwordShowButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if (isValidEmail(email)) {
                    emailEditText.setError(null);
                    passwordEditText.setError(null);
                    // Create user object from model


                    if (password.length() < 8) {
                        passwordEditText.setError(getResources().getString(R.string.ERROR_PASSWORD_COMPLEXITY_LENGTH));
                        return;
                    }
                    UserLoginModel user = new UserLoginModel(email, password);

                    // Authenticate user using ClientAPI
                    new FellowTravellerAPI(globalClass).userLogin(user, new UserAuthCallback() {
                        @Override
                        public void onSuccess(UserAuthModel user) {
                            SaveClass(user);
                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    emailEditText.setError(getResources().getString(R.string.ERROR_INVALID_EMAIL_FORMAT));
                }
            }
        });

        emailEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditText.setText("");
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
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PhoneActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void SaveClass(UserAuthModel userAuth) {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAuth);
        editor.putString(getResources().getString(R.string.USER_INFO), json);
        editor.apply();
        globalClass.setCurrentUser(userAuth);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(emailFlag)
            underlineEmail.setPressed(true);
        else if(passwordFlag)
            underlinePassword.setPressed(true);
    }
}

package com.example.fellow_traveller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import static com.example.fellow_traveller.Utils.InputValidation.isValidEmail;

public class LoginActivity extends AppCompatActivity {


    private Button loginButton;
    private GlobalClass globalClass;
    private TextInputLayout textInputLayout_email, textInputLayout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        globalClass = (GlobalClass) getApplicationContext();
        textInputLayout_email = findViewById(R.id.LoginActivity_editText_email);
        textInputLayout_password = findViewById(R.id.LoginActivity_editText_password);

        loginButton = findViewById(R.id.LoginActivity_button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textInputLayout_email.getEditText().getText().toString();
                String password = textInputLayout_password.getEditText().getText().toString();
                if (isValidEmail(email)) {
                    // TODO Fix error message on input field
                    textInputLayout_email.setError(null);
                    // TODO remove password error from here, this is an email field.
                    textInputLayout_password.setError(null);
                    // Authenticate user using ClientAPI
                    new FellowTravellerAPI(globalClass).userAuthenticate(email, password, new UserAuthCallback() {
                        @Override
                        public void onSuccess(UserAuthModel user) {
                            SaveClass(user);
                            //Log.d("Authentication", user.getSessionId());
                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            // TODO remove this debug line.
                            Log.d("Authentication", "INVALID LOGIN");
                            Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    textInputLayout_email.setError("Invalid email address.");
                }
            }
        });
    }

    public void SaveClass(UserAuthModel userAuth) {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAuth);
        editor.putString(getResources().getString(R.string.USER_INFO), json);
        Log.i("LoginUser", "2");

        editor.apply();
        globalClass.setCurrent_user(userAuth);
        Log.i("LoginUser", "3");

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}

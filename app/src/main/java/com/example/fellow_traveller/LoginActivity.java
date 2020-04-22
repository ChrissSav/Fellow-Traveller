package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuth;
import com.example.fellow_traveller.ClientAPI.UserAuthCallback;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    private Button loginButton;
    private GlobalClass globalClass;
    private TextInputLayout textInputLayout_email,textInputLayout_password;
//Retrofit

    private RetrofitService retrofitService;
    private Retrofit retrofit;

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
                if(isValidEmail(email)){
                    textInputLayout_email.setError(null);
                    textInputLayout_password.setError(null);
                    // Authenticate user using ClientAPI
                    FellowTravellerAPI.userAuthenticate(email, password, new UserAuthCallback() {
                        @Override
                        public void onSuccess(UserAuth user) {
                            SaveClass(user);
                        }

                        @Override
                        public void onFailure() {
                            // TODO What happens when we didn't get any user object?
                            // TODO What happens with the activity?
                            Log.d("Authentication","INVALID LOGIN");
                        }
                    });
                }else {
                    textInputLayout_email.setError("The email is not valid !");
                }
            }
        });
    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void SaveClass(UserAuth userAuth) {
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

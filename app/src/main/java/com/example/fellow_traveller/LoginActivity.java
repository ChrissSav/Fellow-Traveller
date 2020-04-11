package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.UserAuth;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                    LoginUser(email,password);
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

    public void LoginUser(String email,String password) {
        Log.i("LoginUser",email+" "+ password);
        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);
        Log.i("LoginUser",email+" "+ password);
        JsonObject user_object = new JsonObject();
        user_object.addProperty("email", email);
        user_object.addProperty("password", password);
        Log.i("LoginUser","τελοσ ξσον");

        Call<UserAuth> call = retrofitService.loginUser(user_object);
        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                Log.i("LoginUser", "μπηκα");
                if (!response.isSuccessful()) {
                    try {
                        Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Log.i("LoginUser", "ολα καλα");

                UserAuth userAuth = response.body();
                SaveClass(userAuth);


            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Log.i("LoginUser", "φαιλ");

                Log.i("Register_Container", "onFailure: " + t.getMessage());
            }
        });
    }



    public void SaveClass(UserAuth userAuth) {
        Log.i("LoginUser", "σαβε");
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

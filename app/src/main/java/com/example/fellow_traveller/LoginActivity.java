package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
                    //LoginUser(email,password);
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

    /*public void LoginUser(String email, String password) {
        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);


        Log.i("LoginUser", "user_phone :" + email + " " + password);

        JsonObject user_obj = new JsonObject();
        user_obj.addProperty("email", email);
        user_obj.addProperty("password", password);

        Call<User> call = retrofitService.loginUser(user_obj);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "response " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(RegisterContainerActivity.this, "body " + response.body(), Toast.LENGTH_SHORT).show();
                User user = response.body();
                if (user.getName() != null && user.getSurname() != null && user.getId() != 0) {
                    Log.i("onResponse", user.getId() + "\n" + user.getName() + "\n" + user.getSurname());
                    globalClass.setCurrent_user(user);
                    SaveUserInfo(user.getId() + "", user.getName(), user.getSurname());

                } else {
                    textInputLayout_email.setError("Incorrect fields!");
                    textInputLayout_password.setError("Incorrect fields!");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Register_Container", "onFailure: " + t.getMessage());
            }
        });
    }*/

    public void SaveUserInfo(String id, String name, String surname) {
        String final_str = id + "\n" + name + "\n" + surname;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.USER_INFO_FILE), MODE_PRIVATE);
            fos.write(final_str.getBytes());
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

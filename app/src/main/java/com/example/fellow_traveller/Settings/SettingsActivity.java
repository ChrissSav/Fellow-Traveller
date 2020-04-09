package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.LoginActivity;
import com.example.fellow_traveller.MainActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.UserAuth;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {
    private Button personalButton,btn_logout;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private GlobalClass globalClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        globalClass = (GlobalClass) getApplicationContext();

        personalButton = findViewById(R.id.personal_info);
        btn_logout  = findViewById(R.id.logout);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PersonalSettingsActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogOut();
            }
        });
    }

    public void LogOut() {
        Log.i("getAccess_token", globalClass.getCurrent_user().getAccess_token());

        retrofit = new Retrofit.Builder().baseUrl(getResources()
                .getString(R.string.API_URL))
                .client(globalClass.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);
        Call<Status_Handling> call = retrofitService.LogoutUser();
        call.enqueue(new Callback<Status_Handling>() {
            @Override
            public void onResponse(Call<Status_Handling> call, Response<Status_Handling> response) {
                Log.i("SaveClass", "-2");
                if (!response.isSuccessful()) {
                    try {
                        Toast.makeText(SettingsActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Log.i("SaveClass", "-1");
                Log.i("SaveClass", "0");

                Log.i("SaveClass", "4");
                DeleteSharedPreferences();


            }

            @Override
            public void onFailure(Call<Status_Handling> call, Throwable t) {
                Log.i("Register_Container", "onFailure: " + t.getMessage());
            }
        });
    }

    public void DeleteSharedPreferences() {
        SharedPreferences settings = getSharedPreferences("shared preferences", MODE_PRIVATE);
        settings.edit().clear().commit();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

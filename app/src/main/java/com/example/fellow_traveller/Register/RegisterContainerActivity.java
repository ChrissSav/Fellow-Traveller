package com.example.fellow_traveller.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.API.RetrofitService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterContainerActivity extends AppCompatActivity {

    private final int stages = 3;
    private Button buttonNext;
    private ImageButton btnBack;
    private FragmentManager fragmentManager;
    private Fragment fra;
    private RegisterStage1Fragment registerStage1Fragment = new RegisterStage1Fragment();
    private RegisterStage2Fragment registerStage2Fragment = new RegisterStage2Fragment();
    private RegisterStage3Fragment registerStage3Fragment = new RegisterStage3Fragment();
    private ProgressBar progressBar;
    private int num_num;
    private String userPhone;
    private GlobalClass globalClass;
    private DatabaseReference userDatabase;

    //Retrofit

    private RetrofitService retrofitService;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_container);
        globalClass = (GlobalClass) getApplicationContext();


        num_num = 100 / stages;
        userPhone = getIntent().getStringExtra("USER_PHONE");

        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);

        progressBar = findViewById(R.id.RegisterActivity_progressBar);
        buttonNext = findViewById(R.id.RegisterActivity_button_next);
        btnBack = findViewById(R.id.RegisterActivity_imageButton);


        progressBar.setProgress(num_num * registerStage1Fragment.getRank());

        //Fragment Management


        fragmentManager = getSupportFragmentManager();
        fra = registerStage1Fragment;
        fragmentManager.beginTransaction().replace(R.id.RegisterActivity_frame_container, fra).commit();


        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fra.toString().equals("RegisterStage1Fragment") && registerStage1Fragment.validateFragment()) {
                    fra = registerStage2Fragment;
                    progressBar.setProgress(num_num * registerStage2Fragment.getRank());
                    //buttonNext.setText("Αποστολή");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                } else if (fra.toString().equals("RegisterStage2Fragment") && registerStage2Fragment.validateFragment()) {
                    progressBar.setProgress(num_num * registerStage3Fragment.getRank());
                    fra = registerStage3Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                    buttonNext.setText("Complete");

                } else if (fra.toString().equals("RegisterStage3Fragment") && registerStage3Fragment.validateFragment()) {
                    ////   Toast.makeText(RegisterContainerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    RegisterUser();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (fra.toString().equals("RegisterStage3Fragment")) {
            progressBar.setProgress(num_num * registerStage2Fragment.getRank());
            buttonNext.setText("Next");
            fra = registerStage2Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("RegisterStage2Fragment")) {
            progressBar.setProgress(num_num * registerStage1Fragment.getRank());
            fra = registerStage1Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        } else {
            super.onBackPressed();
        }

    }

    public void RegisterUser() {
        final String email = registerStage1Fragment.getEmail();
        final String password = registerStage2Fragment.getPassword();
        String name = registerStage3Fragment.GetName();
        String surname = registerStage3Fragment.GetSurName();


        JsonObject user_obj = new JsonObject();
        user_obj.addProperty("name", name);
        user_obj.addProperty("surname", surname);
        user_obj.addProperty("email", email);
        user_obj.addProperty("password", password);
        user_obj.addProperty("phone",userPhone);

        Call<UserAuthModel> call = retrofitService.registerUser(user_obj);
        call.enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                Log.i("SaveClass", "-2");
                if (!response.isSuccessful()) {
                    try {
                        Toast.makeText(RegisterContainerActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                UserAuthModel userAuth = response.body();
                Save(userAuth);

            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                Log.i("Register_Container", "onFailure: " + t.getMessage());
            }
        });
    }


    private void firebaseRegister(String id, String name, String surname) {
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("surname", surname);
        userMap.put("image", "default");

        userDatabase.setValue(userMap);

    }

    public void Save(UserAuthModel userAuth) {
        firebaseRegister(userAuth.getId()+"", userAuth.getName(), userAuth.getSurname());
        globalClass.SaveClass(userAuth);
        Intent intent = new Intent(RegisterContainerActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}























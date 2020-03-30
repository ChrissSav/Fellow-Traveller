package com.example.fellow_traveller.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.SplashActivity;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterContainerActivity extends AppCompatActivity {

    private final int stages = 3;
    private Button button_next;
    private ImageButton btn_back;
    private FragmentManager fragmentManager;
    private Fragment fra;
    private RegisterStage1Fragment registerStage1Fragment = new RegisterStage1Fragment();
    private RegisterStage2Fragment registerStage2Fragment = new RegisterStage2Fragment();
    private RegisterStage3Fragment registerStage3Fragment = new RegisterStage3Fragment();
    private ProgressBar progressBar;
    private int num_num;
    private String user_phone;
    private GlobalClass globalClass;

    //Retrofit

    private RetrofitService retrofitService;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_container);
        globalClass = (GlobalClass) getApplicationContext();

        num_num = 100 / stages;
        user_phone = getIntent().getStringExtra("USER_PHONE");
        Log.i("Register_Container", "user_phone :" + user_phone);

        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);

        progressBar = findViewById(R.id.RegisterActivity_progressBar);
        button_next = findViewById(R.id.RegisterActivity_button_next);
        btn_back = findViewById(R.id.RegisterActivity_imageButton);


        progressBar.setProgress(num_num * registerStage1Fragment.getRank());

        //Fragment Management


        fragmentManager = getSupportFragmentManager();
        fra = registerStage1Fragment;
        fragmentManager.beginTransaction().replace(R.id.RegisterActivity_frame_container, fra).commit();


        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fra.toString().equals("RegisterStage1Fragment") && registerStage1Fragment.isOk()) {
                    fra = registerStage2Fragment;
                    progressBar.setProgress(num_num * registerStage2Fragment.getRank());
                    //button_next.setText("Αποστολή");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                } else if (fra.toString().equals("RegisterStage2Fragment") && registerStage2Fragment.isOk()) {
                    progressBar.setProgress(num_num * registerStage3Fragment.getRank());
                    fra = registerStage3Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                    button_next.setText("Complete");

                } else if (fra.toString().equals("RegisterStage3Fragment") && registerStage3Fragment.isOk()) {
                    ////   Toast.makeText(RegisterContainerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    RegisterUser();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (fra.toString().equals("RegisterStage3Fragment")) {
            progressBar.setProgress(num_num * registerStage2Fragment.getRank());
            button_next.setText("Next");
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
        final String email = registerStage1Fragment.GetEmail();
        final String password = registerStage2Fragment.GetPassword();
        String name = registerStage3Fragment.GetName();
        String surname = registerStage3Fragment.GetSurName();

        User user = new User(name, surname, email, password, user_phone);
        //Log.i("Register_Container", "user_phone :" + user_phone+"\n "+user.toString());


        Call<Status_Handling> call = retrofitService.registerUser(user);
        call.enqueue(new Callback<Status_Handling>() {
            @Override
            public void onResponse(Call<Status_Handling> call, Response<Status_Handling> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterContainerActivity.this, "response " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Status_Handling st = response.body();
                String res = st.getStatus() + " " + st.getMsg();
                Log.i("Register_Container", res);

                if (st.getStatus().equals("success")) {
                    /*Intent intent = new Intent(RegisterContainerActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
                    LoginUser(email, password);

                } else {
                    Toast.makeText(RegisterContainerActivity.this, st.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Status_Handling> call, Throwable t) {
                Log.i("Register_Container", "onFailure: " + t.getMessage());
            }
        });
    }

    public void SaveUserInfo(String id, String name, String surname) {
        String final_str = id + "\n" + name + "\n" + surname;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(getString(R.string.USER_INFO_FILE), MODE_PRIVATE);
            fos.write(final_str.getBytes());
            Intent intent = new Intent(RegisterContainerActivity.this, HomeActivity.class);
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

    public void LoginUser(String email, String password) {
        Log.i("LoginUser", "user_phone :" + email + " " + password);

        JsonObject user_obj = new JsonObject();
        user_obj.addProperty("email", email);
        user_obj.addProperty("password", password);

        Call<User> call = retrofitService.loginUser(user_obj);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterContainerActivity.this, "response " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(RegisterContainerActivity.this, "body " + response.body(), Toast.LENGTH_SHORT).show();
                User user = response.body();
                if (user.getName() != null && user.getSurname() != null && user.getId() != 0) {
                    Log.i("onResponse", user.getId() + "\n" + user.getName() + "\n" + user.getSurname());
                    globalClass.setCurrent_user(user);
                    SaveUserInfo(user.getId()+"", user.getName(), user.getSurname());


                } else {
                    //error login SaveUserInfo(String id, String name, String surname)


                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Register_Container", "onFailure: " + t.getMessage());
            }
        });
    }
}
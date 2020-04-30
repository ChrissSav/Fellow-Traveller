package com.example.fellow_traveller.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserRegisterCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_container);
        globalClass = (GlobalClass) getApplicationContext();


        num_num = 100 / stages;
        userPhone = getIntent().getStringExtra("USER_PHONE");

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
                    Toast.makeText(RegisterContainerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    newRegisterUser();
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

    // TODO why not call this directly from above instead of having a separate method?
    public void newRegisterUser() {
        String email = registerStage1Fragment.getEmail();
        String password = registerStage2Fragment.getPassword();
        String name = registerStage3Fragment.GetName();
        final String surname = registerStage3Fragment.GetSurName();

        new FellowTravellerAPI(globalClass).userRegister(name, surname, email, password, userPhone, new UserRegisterCallback() {
            @Override
            public void onSuccess(UserAuthModel user) {

                Save(user);
                firebaseRegister(String.valueOf(user.getId()),user.getName(), user.getSurname());
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(RegisterContainerActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
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
        firebaseRegister(userAuth.getId() + "", userAuth.getName(), userAuth.getSurname());
        globalClass.SaveClass(userAuth);
        Intent intent = new Intent(RegisterContainerActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}























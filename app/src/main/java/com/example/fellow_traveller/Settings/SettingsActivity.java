package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.MainActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

public class SettingsActivity extends AppCompatActivity {
    private Button personalButton, btnLogout, changePasswordButton, manageCarsButton;

    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        globalClass = (GlobalClass) getApplicationContext();

        personalButton = findViewById(R.id.personal_info);
        btnLogout = findViewById(R.id.logout);
        manageCarsButton = findViewById(R.id.manage_cars);
        changePasswordButton=findViewById(R.id.change_password);

        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PersonalSettingsActivity.class);
                startActivity(intent);
                //finish();
            }
        });



        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePassword = new Intent(SettingsActivity.this, ChangePassword.class);
                startActivity(changePassword);
            }
        });

        manageCarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, CarsSettingsActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogOut();
            }
        });

    }

    public void LogOut() {
        new FellowTravellerAPI(globalClass).userLogout(new UserLogoutCallBack() {
            @Override
            public void onSuccess(StatusHandleModel user) {
                // TODO handle this one.
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(SettingsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
        DeleteSharedPreferences();


    }

    public void DeleteSharedPreferences() {
        SharedPreferences settings = getSharedPreferences("shared preferences", MODE_PRIVATE);
        settings.edit().clear().commit();
        globalClass.setCurrentUser(null);
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

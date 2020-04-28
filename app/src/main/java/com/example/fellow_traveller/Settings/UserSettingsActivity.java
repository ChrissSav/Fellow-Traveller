package com.example.fellow_traveller.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.MainActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

public class UserSettingsActivity extends AppCompatActivity {
    private GlobalClass globalClass;
    private TextView userFullNameTextView, userEmailAddressTextView;
    private Button personalUserInfoButton, userLogoutButton, manageUserCarsButton, changeUserPasswordButton;
    private ImageButton goBackImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        globalClass = (GlobalClass) getApplicationContext();

        personalUserInfoButton = findViewById(R.id.activity_user_settings_personal_user_info_button);
        userLogoutButton = findViewById(R.id.activity_user_settings_user_logout_button);
        manageUserCarsButton = findViewById(R.id.activity_user_settings_manage_user_cars_button);
        changeUserPasswordButton = findViewById(R.id.activity_user_settings_change_user_password_button);
        goBackImageButton = findViewById(R.id.activity_user_settings_go_back_button);
        userFullNameTextView = findViewById(R.id.activity_user_settings_user_full_name_text_view);
        userEmailAddressTextView = findViewById(R.id.activity_user_settings_user_email_address_text_view);

        goBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personalUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSettingsActivity.this, PersonalSettingsActivity.class);
                startActivity(intent);
                // TODO find out what to do with the commented code below
                //finish();
            }
        });

        changeUserPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePassword = new Intent(UserSettingsActivity.this, ChangePassword.class);
                startActivity(changePassword);
            }
        });

        manageUserCarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSettingsActivity.this, CarsSettingsActivity.class);
                startActivity(intent);
            }
        });

        userLogoutButton.setOnClickListener(new View.OnClickListener() {
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
                // TODO handle this error properly
                Toast.makeText(UserSettingsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        // TODO Q: Is there a way to remove user data in a nicer way from the app?
        deleteSharedPreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
        globalClass = (GlobalClass) getApplicationContext();
        userFullNameTextView.setText(globalClass.getCurrentUser().getFullName());
        userEmailAddressTextView.setText(globalClass.getCurrentUser().getEmailAddress());
    }

    public void deleteSharedPreferences() {
        SharedPreferences settings = getSharedPreferences("shared preferences", MODE_PRIVATE);
        settings.edit().clear().commit();
        globalClass.setCurrentUser(null);
        Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

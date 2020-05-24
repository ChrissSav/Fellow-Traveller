package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Settings.UserSettingsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME = 2000;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        globalClass = (GlobalClass) getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (globalClass.getCurrentUser() != null) {
                    new FellowTravellerAPI(globalClass).getUserInfo(new UserAuthCallback() {
                        @Override
                        public void onSuccess(UserAuthModel user) {
                            String session_id = globalClass.getCurrentUser().getSessionId();
                            user.setSessionId(session_id);
                            SaveClass(user);
                            Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                    });

                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_TIME);
    }

    public void SaveClass(UserAuthModel userAuth) {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAuth);
        editor.putString(getResources().getString(R.string.USER_INFO), json);
        globalClass.setCurrentUser(userAuth);
    }



}

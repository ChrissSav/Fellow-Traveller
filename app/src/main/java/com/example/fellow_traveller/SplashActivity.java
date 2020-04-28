package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME = 2000;
    private GlobalClass globalClass;
    private int acti = 0;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        globalClass = (GlobalClass) getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (globalClass.getCurrentUser() != null) {
                    Intent mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_TIME);
    }

}

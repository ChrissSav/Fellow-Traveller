package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.Models.UserAuth;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.NewOffer.NewOfferStage1Fragment;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.example.fellow_traveller.SearchAndBook.BookActivity;
import com.example.fellow_traveller.SearchAndBook.Search2Activity;
import com.example.fellow_traveller.SearchAndBook.SearchActivity;
import com.example.fellow_traveller.SearchAndBook.SearchDetailsActivity;
import com.example.fellow_traveller.SearchAndBook.SearchResultsActivity;
import com.example.fellow_traveller.Trips.TripPageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

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

                if (globalClass.getCurrent_user() != null) {
                    Intent mainIntent = new Intent(SplashActivity.this, Search2Activity.class);
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

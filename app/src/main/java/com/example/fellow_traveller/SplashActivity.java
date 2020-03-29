package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.Register.RegisterContainerActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this, RegisterContainerActivity.class);
                startActivity(mainIntent);
                finish();

            }
        }, SPLASH_TIME);*/

        Thread splashTread = new Thread() {
            @Override
            public void run() {

                while (flag == false) {
                    try {
                        sleep(SPLASH_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Load();
                }
                if (globalClass.getCurrent_user() != null) {
                    Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };
        splashTread.start();
    }

    public void Load() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(getString(R.string.USER_INFO_FILE));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            int i = 0;
            User user = new User();

            while ((text = br.readLine()) != null) {
                // Log.i("Load_Info1", "text:" + text + " i = " + i);
                sb.append(text).append("\n");
                if (i == 0) {
                    user.setId(Integer.parseInt(text));
                } else if (i == 1) {
                    user.setName(text);
                } else if (i == 2) {
                    user.setSurname(text);
                }
                i++;
            }
            globalClass.setCurrent_user(user);
            //Log.i("Load_Info", "\ntext:" + sb.toString() + "\n" + sb);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        flag = true;
    }
}

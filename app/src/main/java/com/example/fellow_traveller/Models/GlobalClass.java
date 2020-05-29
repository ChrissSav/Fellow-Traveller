package com.example.fellow_traveller.Models;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GlobalClass extends Application {
    public static final String CHANNEL_1_ID = "passenger_notification";
    private static final String CHANNEL_NAME_1 = "Σε αυτο το κανάλι θα ερχονται οι ειδποιησεις για αξιολογηση και αν προστεθηκε νεος εππιβατης";
    private UserAuthModel currentUser;
    private OkHttpClient.Builder okHttpClient;


    public UserAuthModel getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserAuthModel currentUser) {
        this.currentUser = currentUser;
    }


    public OkHttpClient.Builder getOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder().proxy(Proxy.NO_PROXY);
        okHttpClient.addInterceptor((new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder newRequest = request.newBuilder();
                try {
                    newRequest.header("Cookie", currentUser.getSessionId());
                } catch (NullPointerException e) {
                    // do something other
                }
                return chain.proceed(newRequest.build());
            }
        }));
        return okHttpClient;
    }


    @Override
    public void onCreate() {
        LoadClass();
        createNotificationChannels();
        scheduleJob();

        super.onCreate();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Κανάλι ειδοποιήσεων",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription(CHANNEL_NAME_1);
            //channel1.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel1.enableVibration(true);
            channel1.setShowBadge(false);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager == null) {
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            manager.createNotificationChannel(channel1);
        }
    }

    public void LoadClass() {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(getResources().getString(R.string.USER_INFO), null);
        Type type = new TypeToken<UserAuthModel>() {
        }.getType();
        UserAuthModel userAuth = gson.fromJson(json, type);
        currentUser = userAuth;
    }

    public void SaveClass(UserAuthModel userAuth) {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAuth);
        editor.putString(getResources().getString(R.string.USER_INFO), json);
        Log.i("SaveClass", "2");
        editor.apply();
        currentUser = userAuth;
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(1232, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }


}


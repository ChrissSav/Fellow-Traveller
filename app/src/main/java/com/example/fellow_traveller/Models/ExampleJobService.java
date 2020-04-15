package com.example.fellow_traveller.Models;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.LoginActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Trips.TripPageActivity;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.fellow_traveller.Models.GlobalClass.CHANNEL_1_ID;

public class ExampleJobService extends JobService {

    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;
    private NotificationManagerCompat notificationManager;


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "ExampleJobService started");
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1; i++) {
                    Log.d(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }

                    //CheckConnection();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ViewNotif("run: " + i,i);
                }

                Log.d(TAG, "ExampleJobService finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "ExampleJobService cancelled before completion");
        jobCancelled = true;
        return true;
    }

    public void CheckConnection() {

        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);

        JsonObject user_object = new JsonObject();
        user_object.addProperty("refresh_token", "hkyukuykyukuyk");
        Call<Status_Handling> call = retrofitService.Test();
        call.enqueue(new Callback<Status_Handling>() {
            @Override
            public void onResponse(Call<Status_Handling> call, Response<Status_Handling> response) {

                Log.i(TAG, "-2");
                if (!response.isSuccessful()) {
                    // Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "-1");

                    return;
                }
                //Log.i(TAG, "-1");

                ///Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Status_Handling> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());

            }

        });
    }

    public void ViewNotif(String text,int i ){
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String user ="user";
        String icon = "user";
        String title ="user";
        String body = "user";
        Log.i(TAG, "ViewNotif");
        Context mContext = this;
        notificationManager = NotificationManagerCompat.from(mContext);


        Intent activityIntent = new Intent(this, TripPageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);



        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle("Ειδποποιήση ταξιδιού")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(i, notification);

    }
}

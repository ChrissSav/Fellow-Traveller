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
import android.provider.Settings;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;
import static com.example.fellow_traveller.Models.GlobalClass.CHANNEL_1_ID;


public class ExampleJobService extends JobService {
    public static final String TITLE = "Ειδποποιήση ταξιδιού";
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
                for (int i = 0; i < 2; i++) {

                    if (jobCancelled) {
                        return;
                    }

                    //CheckConnection();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "run: " + i);
                    CheckConnection();
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
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).client(globalClass.getOkHttpClient().build()).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);

        Call<List<NotificationModel>> call = retrofitService.getNotifications();
        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "!response.isSuccessful()");


                    return;
                }
                List<NotificationModel> list = response.body();

                for (NotificationModel notificationModel: list){
                    String text = "Ο χρήστης "+notificationModel.getUser().getName()+ " "+notificationModel.getUser().getSurname()
                            + " μόλις προστεθηκε στο ταξίδι σου";
                    ViewNotification( text, notificationModel.getId());
                }




            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());

            }

        });
    }

    public void ViewNotification(String text, int i) {

        Log.i(TAG, "ViewNotification");
        Context mContext = this;

        notificationManager = NotificationManagerCompat.from(mContext);


        Intent activityIntent = new Intent(this, TripPageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(TITLE)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setOnlyAlertOnce(true)
                .setVibrate(new long[] { 1000, 1000})
                .build();

        notificationManager.notify(i, notification);

    }


}
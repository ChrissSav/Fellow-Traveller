package com.example.fellow_traveller.Models;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fellow_traveller.ClientAPI.Callbacks.NotificationCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Trips.TripPageDriverActivity;

import java.util.ArrayList;

import static com.example.fellow_traveller.Models.GlobalClass.CHANNEL_1_ID;


public class ExampleJobService extends JobService {
    public static final String TITLE = "Fellow Traveller";
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;
    private NotificationManagerCompat notificationManager;
    private GlobalClass globalClass;


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
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }
                    CheckConnection();
                    try {
                        Thread.sleep(25000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Log.d(TAG, "Job finished");
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
        globalClass = (GlobalClass) getApplicationContext();
        new FellowTravellerAPI(globalClass).getNotifications(new NotificationCallBack() {
            @Override
            public void onSuccess(ArrayList<NotificationModel> notificationModels) {
                for (NotificationModel notificationModel: notificationModels){
                    String text = "Ο χρήστης "+notificationModel.getUser().getFirstName()+ " "+notificationModel.getUser().getLastName()
                            + " μόλις προστεθηκε στο ταξίδι σου";
                    ViewNotification( text, notificationModel.getId(),notificationModel);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    public void ViewNotification(String text, int i,NotificationModel notificationModel) {

        Context mContext = this;

        notificationManager = NotificationManagerCompat.from(mContext);


        Intent activityIntent = new Intent(this, TripPageDriverActivity.class);
        activityIntent.putExtra("trip",notificationModel.getTrip());
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(TITLE)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text)
                        .setBigContentTitle("Προστέθηκε επιβάτης"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();


        notificationManager.notify(i, notification);

    }


}

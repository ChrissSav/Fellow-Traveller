package com.example.fellow_traveller.Models;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.net.Proxy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalClass extends Application {
    public static final String CHANNEL_1_ID = "channel";
    private static final String CHANNEL_NAME_1 = "This is Channel 1";


    private UserAuth current_user;
    private OkHttpClient.Builder okHttpClient;

    public UserAuth getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UserAuth current_user) {
        this.current_user = current_user;
    }

    public OkHttpClient.Builder getOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder().proxy(Proxy.NO_PROXY);
        okHttpClient.addInterceptor((new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("getAccess_token", current_user.getAccess_token());

                Request.Builder newRequest = request.newBuilder().header("authorization", current_user.getAccess_token());
                return chain.proceed(newRequest.build());
            }
        }));
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        createNotificationChannels();

        super.onCreate();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_MAX
            );
            channel1.setDescription(CHANNEL_NAME_1);

            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager  manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel1);



        }
    }
}


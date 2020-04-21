package com.example.fellow_traveller.Models;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fellow_traveller.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalClass extends Application {

    private UserAuth current_user;
    private OkHttpClient.Builder okHttpClient;

    public UserAuth getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UserAuth current_user) {
        this.current_user = current_user;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LoadClass();
    }


    public OkHttpClient.Builder getOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder().proxy(Proxy.NO_PROXY);
        okHttpClient.addInterceptor((new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("getAccess_token", current_user.getAccess_token());

                Request.Builder newRequest = request.newBuilder().header("authorization",current_user.getAccess_token());
                return chain.proceed(newRequest.build());
            }
        }));
        return okHttpClient;
    }

    public void LoadClass() {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(getResources().getString(R.string.USER_INFO), null);
        Type type = new TypeToken<UserAuth>() {
        }.getType();
        UserAuth userAuth = gson.fromJson(json, type);
        current_user = userAuth;
    }
}


package com.example.fellow_traveller.Models;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
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

    public OkHttpClient.Builder getOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder();
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
}


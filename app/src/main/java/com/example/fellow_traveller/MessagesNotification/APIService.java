package com.example.fellow_traveller.MessagesNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({

            "Content-Type:application/json",
            "Authorization:key=AAAAaYTY6wU:APA91bHJ97K48571tdUDDCuaptVBIdSL6kOZrM_LWIy6Qej02FMx4HypDXZ8CO1cb5GzzW8tP7hYXiuGY6NVf8nmLt95KVM5zspOzgVuocsWxglnmKDr19jhsyvQYT-N6B5nkwvTAhzE"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender boyd);
}

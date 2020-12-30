package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({

            "Content-Type:application/json",
            "Authorization:key=AAAAA8MSR2E:APA91bH_3H3fKATLsgjsXfHSuxjICN8icSjFCtVGXGoz6sQUhrwkci6jqIv-TdaAxH2t5d6DOZggJO_uEpvd8bcpk6ST7sqknLZ7HVox2n69Z1AHostyQ4zkb-n2G1c9lgE6GVlRZLml"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender boyd);
}

package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    private var retrofit: Retrofit? = null

    fun getClient(url: String): Retrofit? {

        if (retrofit == null) {

            retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}
package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import com.example.fellowtravellerbeta.data.network.google.PlaceApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.Proxy
import javax.inject.Singleton
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }


    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        sharedPreferences: SharedPreferences
    ): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder().proxy(Proxy.NO_PROXY)
        okHttpClient
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                    try {
                        newRequest.header("Cookie", sharedPreferences.getString("Cookie", "")!!)
                    } catch (e: NullPointerException) {
                        // do something other
                    }
                    return chain.proceed(newRequest.build())
                }
            })
        okHttpClient.addInterceptor(loggingInterceptor!!)
        return okHttpClient
    }


    @Singleton
    @Provides
    fun provideConnectivityHelper(application: Application): ConnectivityHelper {
        val connectivityManager =
            application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityHelper(connectivityManager)
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient.Builder): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
    }

    @Singleton
    @Provides
    fun provideFellowService(retrofit: Retrofit.Builder): FellowService {
        return retrofit.baseUrl("https://api.fellowtraveller.gr/").build()
            .create(FellowService::class.java)
    }

    @Singleton
    @Provides
    fun provideFellowGoogleService(retrofit: Retrofit.Builder): PlaceApiService {
        return retrofit
            .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
            .build()
            .create(PlaceApiService::class.java)
    }

}


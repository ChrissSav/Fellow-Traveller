package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson = GsonBuilder().create()


    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }


    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val apply = httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS }
        return apply.apply { apply.level = HttpLoggingInterceptor.Level.BODY }
    }


    @Fellow
    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, sharedPreferences: SharedPreferences): OkHttpClient.Builder {
        return OkHttpClient.Builder().proxy(Proxy.NO_PROXY)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                    var session: String? = null
                    session = sharedPreferences.getString(PREFS_AUTH_TOKEN, "")
                    session = sharedPreferences.getString(PREFS_AUTH_TOKEN, "")
                    session?.let {
                        newRequest.header("Cookie", it)
                    }
                    return chain.proceed(newRequest.build())
                }
            })
            .addInterceptor(loggingInterceptor)

    }


    @Singleton
    @Provides
    fun provideConnectivityHelper(application: Application): ConnectivityHelper {
        val connectivityManager = application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityHelper(connectivityManager)
    }


    @Fellow
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @Fellow okHttpClient: OkHttpClient.Builder): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
    }

    @Singleton
    @Provides
    fun provideFellowService(@Fellow retrofit: Retrofit.Builder): FellowService {
        return retrofit.baseUrl("https://api.fellowtraveller.gr/").build()
            .create(FellowService::class.java)
    }


    /**
     * Google Service
     * */

    @GoogleService
    @Singleton
    @Provides
    fun provideOkHttpClientGoogle(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder().proxy(Proxy.NO_PROXY)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)

    }


    @GoogleService
    @Singleton
    @Provides
    fun provideRetrofitGoogle(gson: Gson, @GoogleService okHttpClient: OkHttpClient.Builder): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
    }


    @Singleton
    @Provides
    fun provideFellowGoogleService(@GoogleService retrofit: Retrofit.Builder): PlaceApiService {
        return retrofit
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .build()
            .create(PlaceApiService::class.java)
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Fellow

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleService

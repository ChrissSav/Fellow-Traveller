package gr.fellow.fellow_traveller.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.fellow.fellow_traveller.BuildConfig
import gr.fellow.fellow_traveller.framework.NetworkConnectionInterceptor
import gr.fellow.fellow_traveller.framework.TokenInterceptor
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.fellow.FellowTokenService
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
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

    @Singleton
    @Provides
    fun provideTokenInterceptor(sharedPreferences: SharedPreferences, fellowTokenService: FellowTokenService): TokenInterceptor {
        return TokenInterceptor(sharedPreferences, fellowTokenService)
    }


    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()


    @Singleton
    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Fellow
    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, connectivityHelper: ConnectivityHelper, tokenInterceptor: TokenInterceptor
    ): OkHttpClient.Builder {
        val client = OkHttpClient.Builder().proxy(Proxy.NO_PROXY)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(connectivityHelper))
            .addInterceptor(tokenInterceptor)
        if (BuildConfig.DEBUG) {
            client.addInterceptor(loggingInterceptor)
        }
        return client

    }


    @Singleton
    @Provides
    fun provideConnectivityHelper(application: Application): ConnectivityHelper {
        return ConnectivityHelper(application.applicationContext)
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
        return retrofit.baseUrl(BuildConfig.FELLOW_API_URL).build()
            .create(FellowService::class.java)
    }

    @Singleton
    @Provides
    fun provideFellowTokenService(@GoogleService retrofit: Retrofit.Builder): FellowTokenService {
        return retrofit.baseUrl(BuildConfig.FELLOW_API_URL).build()
            .create(FellowTokenService::class.java)
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
            .baseUrl(BuildConfig.PLACE_API_URL)
            .build()
            .create(PlaceApiService::class.java)
    }

}


package com.example.fellowtravellerbeta.di

import com.example.fellowtravellerbeta.BuildConfig
import com.example.fellowtravellerbeta.data.network.ApiRepository
import com.example.fellowtravellerbeta.data.network.FellowTravellerApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.sin


val networkModule = module {
    single { provideRetrofit<FellowTravellerApiService>(get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { ApiRepository(get()) }

}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
    //if (BuildConfig.DEBUG) {
    okHttpClient.addInterceptor(httpLoggingInterceptor)
    // }
    return okHttpClient.build()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

inline fun <reified T> provideRetrofit(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.fellowtraveller.gr/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
    return retrofit.create(T::class.java)
}
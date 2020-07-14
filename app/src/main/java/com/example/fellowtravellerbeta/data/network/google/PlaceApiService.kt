package com.example.fellowtravellerbeta.data.network.google

import com.example.fellowtravellerbeta.data.network.google.response.PlaceApiResponse
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


const val key = "AIzaSyBJ8UG-fR3GSjfbfAcatZezGZi2Y8FDzqA"
const val language = "el"
const val components = "country:gr"

interface PlaceApiService {


    @GET("json?components=$components&language=$language&key=$key")
    suspend fun getPlaces(
        @Query("input") input: String
    ): Response<PlaceApiResponse>

}
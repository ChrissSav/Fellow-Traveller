package com.example.fellowtravellerbeta.data.network.google

import com.example.fellowtravellerbeta.data.network.google.response.PlaceApiResponse
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import retrofit2.Response

class PlaceApiRepository(
    private val service: PlaceApiService
) {
    suspend fun getPlacesFromService(place: String): Response<PlaceApiResponse> {
        return service.getPlaces(place)
    }
}
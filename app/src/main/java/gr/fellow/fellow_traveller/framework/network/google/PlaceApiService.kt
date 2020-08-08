package gr.fellow.fellow_traveller.framework.network.google

import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.utils.components
import gr.fellow.fellow_traveller.utils.key
import gr.fellow.fellow_traveller.utils.language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PlaceApiService {

    @GET("autocomplete/json?components=$components&language=$language&key=$key")
    suspend fun getPlaces(
        @Query("input") input: String
    ): Response<PlaceApiResponse>

    @GET("details/json?components=$components&language=$language&key=$key")
    suspend fun getPlacesLanLon(
        @Query("placeid") input: String
    ): Response<DetailsResponse>

}
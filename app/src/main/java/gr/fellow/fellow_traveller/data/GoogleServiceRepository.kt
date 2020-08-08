package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response

interface GoogleServiceRepository {

    suspend fun getPlaces(place: String): Response<PlaceApiResponse>

    suspend fun getPlacesLanLon(placeId: String): Response<DetailsResponse>

}
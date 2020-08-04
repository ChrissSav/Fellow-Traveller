package gr.fellow.fellow_traveller.framework.network.google

import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.utils.performCallWithOut
import retrofit2.Response

class PlaceApiRepository(
    private val service: PlaceApiService
) {
    suspend fun getPlacesFromService(place: String): Response<PlaceApiResponse> {
        return performCallWithOut {
            service.getPlaces(place)
        }
    }
}
package gr.fellow.fellow_traveller.framework.network.google

import com.example.fellowtravellerbeta.data.network.google.PlaceApiService
import com.example.fellowtravellerbeta.data.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.framework.performCallWithOut
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
package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.data.GoogleServiceRepository
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import gr.fellow.fellow_traveller.utils.networkCallWithOutWrap
import retrofit2.Response

class GoogleServiceRepositoryImpl(
    private val connectivityHelper: ConnectivityHelper,
    private val service: PlaceApiService
) : GoogleServiceRepository {


    override suspend fun getPlaces(place: String): Response<PlaceApiResponse> =
        networkCallWithOutWrap(connectivityHelper) {
            service.getPlaces(place)
        }

    override suspend fun getPlacesLanLon(placeId: String): Response<DetailsResponse> =
        networkCallWithOutWrap(connectivityHelper) {
            service.getPlacesLanLon(placeId)
        }

}
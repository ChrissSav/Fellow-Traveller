package gr.fellow.fellow_traveller.usecase.newtrip

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import retrofit2.Response

class GetGeometryFormPlaceUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(place: String): Response<DetailsResponse> {
        return dataSource.getPlacesLanLon(place)
    }
}

package gr.fellow.fellow_traveller.usecase.newtrip

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response

class GetPlacesAutocompleteUseCase(
    private val dataSource: FellowDataSource,
) {

    suspend operator fun invoke(place: String): Response<PlaceApiResponse> {
        return dataSource.getPlaces(place)
    }
}

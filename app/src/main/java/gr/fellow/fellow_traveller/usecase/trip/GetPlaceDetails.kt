package gr.fellow.fellow_traveller.usecase.trip

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetPlaceDetails(
    private val dataSource: FellowDataSource,
) {

    suspend operator fun invoke(placeId: String, type: String) =
        dataSource.getPlaceDetails(placeId, type)
}
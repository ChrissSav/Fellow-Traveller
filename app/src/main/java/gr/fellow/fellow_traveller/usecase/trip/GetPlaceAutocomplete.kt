package gr.fellow.fellow_traveller.usecase.trip

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetPlaceAutocomplete(
    private val dataSource: FellowDataSource,
) {

    suspend operator fun invoke(query: String, type: String) =
        dataSource.getPlaceAutocomplete(query, type)
}
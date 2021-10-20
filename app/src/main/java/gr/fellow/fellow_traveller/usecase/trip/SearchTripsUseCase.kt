package gr.fellow.fellow_traveller.usecase.trip

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.SearchTripFilter

class SearchTripsUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(query: SearchTripFilter) =
        dataSource.searchTrips(query)

}
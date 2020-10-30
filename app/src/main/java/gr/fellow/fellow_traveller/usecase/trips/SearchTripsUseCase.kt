package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.SearchFilters

class SearchTripsUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(query: SearchFilters) =
        dataSource.searchTrips(query)

}
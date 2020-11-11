package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.domain.FellowDataSource

class ExitFromTripUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(tripId: String) =
        dataSource.exitFromTrip(tripId)
}
package gr.fellow.fellow_traveller.usecase.trip

import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteTripUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(tripId: String) =
        dataSource.deleteTrip(tripId)
}
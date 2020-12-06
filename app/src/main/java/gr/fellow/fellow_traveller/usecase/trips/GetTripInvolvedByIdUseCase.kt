package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetTripInvolvedByIdUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(tripId: String) =
        dataSource.getTripInvolvedById(tripId)
}
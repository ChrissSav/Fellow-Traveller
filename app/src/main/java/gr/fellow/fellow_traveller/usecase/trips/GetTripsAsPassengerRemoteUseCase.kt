package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetTripsAsPassengerRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): ResultWrapper<MutableList<Trip>> =
        dataSource.getTipsAsPassenger()
}
package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.trip.TripInvolved

class GetTripsAsPassengerRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): ResultWrapper<MutableList<TripInvolved>> =
        dataSource.getTipsAsPassenger()
}
package gr.fellow.fellow_traveller.usecase.trip

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetTripsAsPassengerRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(status: String) =
        dataSource.getTipsAsPassenger(status)

}
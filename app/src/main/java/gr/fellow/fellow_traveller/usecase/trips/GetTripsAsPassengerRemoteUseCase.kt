package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetTripsAsPassengerRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(status: String, page: Int) =
        dataSource.getTipsAsPassenger(status, page)

}
package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse

class GetTripsAsCreatorRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): ResultWrapper<MutableList<TripResponse>> =
        dataSource.getTipsAsCreator()
}
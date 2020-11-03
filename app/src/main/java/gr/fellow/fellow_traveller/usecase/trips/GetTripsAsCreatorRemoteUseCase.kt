package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse

class GetTripsAsCreatorRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): ResultWrapper<MutableList<TripInvolved>> =
        //dataSource.getTipsAsCreator()
        ResultWrapper.Error(ErrorResponse(1))

}
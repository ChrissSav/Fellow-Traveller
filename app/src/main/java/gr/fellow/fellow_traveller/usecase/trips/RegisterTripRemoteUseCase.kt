package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.request.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse

class RegisterTripRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(
        destFrom: String, destTo: String, pickupPoint: String, timestamp: Long,
        hasPet: Boolean, maxSeats: Int, maxBags: Int, msg: String, price: Float, carId: Int
    ): ResultWrapper<TripResponse> {
        return dataSource.addTripRemote(
            TripCreateRequest(destFrom, destTo, pickupPoint, timestamp, hasPet, maxSeats, maxBags, msg, price, carId)
        )
    }

}
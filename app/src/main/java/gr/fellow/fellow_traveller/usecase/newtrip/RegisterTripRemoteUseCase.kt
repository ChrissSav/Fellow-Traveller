package gr.fellow.fellow_traveller.usecase.newtrip

import gr.fellow.fellow_traveller.domain.FellowDataSource

class RegisterTripRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(
        destFrom: String, destTo: String, carId: String,
        hasPet: Boolean, seats: Int, bags: Int, msg: String?, price: Float, timestamp: Long
    ) =

        dataSource.addTripRemote(destFrom, destTo, carId, hasPet, seats, bags, msg, price, timestamp)


}
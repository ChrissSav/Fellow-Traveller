package gr.fellow.fellow_traveller.usecase.trip

import gr.fellow.fellow_traveller.domain.FellowDataSource

class BookTripUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(tripId: String, seats: Int, pet: Boolean) =
        dataSource.bookTrip(tripId, seats, pet)


}
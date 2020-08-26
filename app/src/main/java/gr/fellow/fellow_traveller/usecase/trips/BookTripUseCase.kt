package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.request.BookTripRequest

class BookTripUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(tripId: Int, bags: Int, pet: Boolean): ResultWrapper<Trip> {
        return dataSource.bookTrip(BookTripRequest(tripId, bags, pet))
    }

}
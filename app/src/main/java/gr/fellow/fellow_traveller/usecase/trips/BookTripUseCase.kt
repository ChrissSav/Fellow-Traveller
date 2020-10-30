package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse

class BookTripUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(tripId: Int, bags: Int, pet: Boolean): ResultWrapper<TripSearch> {
        // return dataSource.bookTrip(BookTripRequest(tripId, bags, pet))
        return ResultWrapper.Error(ErrorResponse(1))
    }

}
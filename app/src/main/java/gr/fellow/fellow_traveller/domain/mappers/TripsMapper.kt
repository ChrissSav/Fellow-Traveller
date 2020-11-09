package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.fellow.response.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.trip.TripSearchResponse


fun TripInvolvedResponse.mapTripInvolved() = TripInvolved(
    id, destFrom, destTo, creator.toUserBase(), car.mapToCarInfoBase(), hasPet, seats, bags, msg, price, timestamp,
    passengers.mapToPassenger(), status
)


fun TripSearchResponse.mapTripSearch() = TripSearch(
    id, destFrom, destTo, creator.toUserBase(), car.mapToCarBase(), hasPet, seats, bags, msg, price, timestamp,
    passengers.mapToPassenger()
)
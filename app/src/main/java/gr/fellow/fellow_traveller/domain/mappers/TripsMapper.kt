package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripSearchResponse


fun TripInvolvedResponse.mapTripInvolved() = TripInvolved(
    id, destFrom, destTo, creator.mapToUserCreatorResponse(), car.mapToCarInfoBase(), hasPet, seats, bags, msg, price, timestamp,
    passengers.mapToPassenger(), status
)


fun TripSearchResponse.mapTripSearch() = TripSearch(
    id, destFrom, destTo, creator.mapToUserCreatorResponse(), car.mapToCarBase(), hasPet, seats, bags, msg, price, timestamp,
    passengers.mapToPassenger()
)
package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse



fun TripResponse.mapTripInvolved() = TripInvolved(
    id, destFrom, destTo, creatorUser.toUserBase(), car.mapToCar(), hasPet, seats, bags, msg, price, timestamp,
    passengers.mapToPassenger()
)
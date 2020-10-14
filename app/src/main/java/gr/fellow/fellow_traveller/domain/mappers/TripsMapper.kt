package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse


fun TripResponse.mapTripInvolved() = TripInvolved(
    id, destFrom, destTo, pickupPoint, creatorUser.toUserBase(), car.mapToCar(),
    passengers.mapToPassenger(), timestamp, hasPet, maxSeats, currentSeats, maxBags, currentBags, msg, price
)
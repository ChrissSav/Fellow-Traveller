package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.fellow.place.PlaceAutocompleteResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.DestinationResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripSearchResponse


fun TripInvolvedResponse.mapTripInvolved() = TripInvolved(
    id, destFrom.mapDestination(), destTo.mapDestination(), destPickUp.mapDestination(), creator.mapToUserCreatorResponse(), car.mapToCarInfoBase(), hasPet, seats,
    getBagsStatus(), msg, price, timestamp, passengers.mapToPassenger(), getTripStatus()
)


fun TripSearchResponse.mapTripSearch() = TripSearch(
    id, destFrom, destTo, creator.mapToUserCreatorResponse(), car.mapToCarBase(), hasPet, seats,
    getBagsStatus(), msg, price, timestamp, passengers.mapToPassenger()
)

fun PlaceAutocompleteResponse.mapDestination() = Destination(placeId, description, null, null)

fun DestinationResponse.mapDestination() = Destination(id, title, latitude, longitude)

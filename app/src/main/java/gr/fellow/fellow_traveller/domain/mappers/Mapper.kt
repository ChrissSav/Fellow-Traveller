package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.framework.network.fellow.response.PassengerResponse


fun PassengerResponse.toPassenger() = Passenger(
    user.toUserBase(), bags, pet
)

/*
fun TripResponse.toTrip(): Trip {
    val passengersTemp = mutableListOf<Passenger>()
    try {
        if (!passengers.isNullOrEmpty())
            this.passengers.forEach { passengersTemp.add(it.toPassenger()) }
    } catch (e: Exception) {

    }

val f = Trip(3)
    return Trip()
        /*this.id, this.destFrom, this.destTo, this.pickupPoint,
        this.creatorUser.toUserBase(), this.car.mapToBaseCar(), passengersTemp,
        this.timestamp, this.hasPet, this.maxSeats, this.currentSeats,
        this.maxBags, this.currentBags, this.msg, this.price*/


}

fun MutableList<TripResponse>.toTrips(): MutableList<Trip> {
    val trips = mutableListOf<Trip>()
    if (isNotEmpty())
        forEach { trips.add(it.toTrip()) }
    return trips
}
*/


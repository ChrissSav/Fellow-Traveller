package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.framework.network.fellow.response.PassengerResponse

fun PassengerResponse.mapToPassenger() = Passenger(
    user.toUserBase(), bags, pet
)

fun MutableList<PassengerResponse>.mapToPassenger(): MutableList<Passenger> {
    if (this.isNullOrEmpty())
        return mutableListOf()
    return this.map { it.mapToPassenger() }.toMutableList()
}
package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.data.models.Car
import gr.fellow.fellow_traveller.data.models.Passenger
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.data.models.UserBase
import gr.fellow.fellow_traveller.framework.network.fellow.response.*
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity


fun UserLoginResponse.toRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate, reviews, picture, aboutMe, phone, emailAddress
)

fun CarResponse.toCarEntity() = CarEntity(
    id, brand, model, plate, color
)

fun CarResponse.toCar() = Car(
    brand, model
)

fun UserBaseResponse.toUserBase() = UserBase(
    id, FirstName, LastName, picture, rate, reviews
)

fun PassengerResponse.toPassenger() = Passenger(
    user.toUserBase(), bags, pet
)


fun TripResponse.toTrip(): Trip {
    val passengersTemp = mutableListOf<Passenger>()
    //if (passengers.isNotEmpty())
    //    this.passengers.forEach { passengersTemp.add(it.toPassenger()) }

    return Trip(
        this.id, this.destFrom, this.destTo, this.pickupPoint,
        this.creatorUser.toUserBase(), this.car.toCar(), passengersTemp,
        this.timestamp, this.hasPet, this.maxSeats, this.currentSeats,
        this.maxBags, this.currentBags, this.msg, this.price
    )

}

fun MutableList<TripResponse>.toTrips(): MutableList<Trip> {
    val trips = mutableListOf<Trip>()
    if (isNotEmpty())
        forEach { trips.add(it.toTrip()) }
    return trips
}
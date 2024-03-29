package gr.fellow.fellow_traveller.domain.trip

import android.os.Parcelable
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.car.CarBase
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserCreator
import gr.fellow.fellow_traveller.framework.network.fellow.trip.DestinationResponse
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp
import gr.fellow.fellow_traveller.utils.getTimeFromTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripSearch(
    val id: String,
    val destFrom: DestinationResponse,
    val destTo: DestinationResponse,
    val creatorUser: UserCreator,
    val carBase: CarBase,
    val hasPet: Boolean,
    val seats: Int,
    val bags: BagsStatusType,
    val msg: String?,
    val price: Float,
    val timestamp: Long,
    val passengers: MutableList<Passenger>
) : Parcelable {
    val date
        get() = getDateFromTimestamp(timestamp)


    val time
        get() = getTimeFromTimestamp(timestamp)


    val seatsStatus: String
        get() {
            var current = 0
            passengers.forEach {
                current += it.seats
            }
            return "${current}/${seats}"
        }

    val vacancies: Int
        get() {
            var current = 0
            passengers.forEach {
                current += it.seats
            }
            return seats - current
        }

}
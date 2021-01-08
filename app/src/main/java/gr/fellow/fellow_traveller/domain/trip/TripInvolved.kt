package gr.fellow.fellow_traveller.domain.trip

import android.os.Parcelable
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.car.CarInfoBase
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserCreator
import gr.fellow.fellow_traveller.framework.network.fellow.trip.DestinationResponse
import gr.fellow.fellow_traveller.utils.convertTimestampToFormat
import gr.fellow.fellow_traveller.utils.getTimeFromTimestamp
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TripInvolved(
    val id: String,
    val destFrom: DestinationResponse,
    val destTo: DestinationResponse,
    val creatorUser: UserCreator,
    val car: CarInfoBase,
    val hasPet: Boolean,
    val seats: Int,
    val bags: BagsStatusType,
    val msg: String?,
    val price: Float,
    val timestamp: Long,
    val passengers: MutableList<Passenger>,
    val picture: String,
    val status: TripStatus,
) : Parcelable {


    val date
        get() = convertTimestampToFormat(timestamp, "EEE, d MMM yyyy")


    val time
        get() = getTimeFromTimestamp(timestamp)


    val placesInfo
        get() = "${destFrom.title} - ${destTo.title}"

    val seatsStatus: String
        get() {
            var current = 0
            passengers.forEach {
                current += it.seats
            }
            return "${current}/${seats}"
        }

}
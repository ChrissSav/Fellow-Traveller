package gr.fellow.fellow_traveller.domain.trip

import android.os.Parcelable
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.framework.network.fellow.response.DestinationResponse
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp
import gr.fellow.fellow_traveller.utils.getTimeFromTimestamp
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TripInvolved(
    val id: Int,
    val destFrom: DestinationResponse,
    val destTo: DestinationResponse,
    val pickupPoint: DestinationResponse,
    val creatorUser: UserBase,
    val carBase: Car,
    val passengers: MutableList<Passenger>,
    val timestamp: Long,
    val hasPet: Boolean,
    val maxSeats: Int,
    val currentSeats: Int,
    val maxBags: Int,
    val currentBags: Int,
    val msg: String?,
    val price: Float
) : Parcelable {


    val date
        get() = getDateFromTimestamp(timestamp)


    val time
        get() = getTimeFromTimestamp(timestamp)

}
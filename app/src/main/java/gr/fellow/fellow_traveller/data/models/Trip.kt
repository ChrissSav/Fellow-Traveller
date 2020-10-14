package gr.fellow.fellow_traveller.data.models

import android.os.Parcelable
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.framework.network.fellow.response.DestinationResponse
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Trip(
    val id: Int,
    val destFrom: DestinationResponse,
    val destTo: DestinationResponse,
    val pickupPoint: DestinationResponse,
    val creatorUser: UserBase,
    val carBase: CarBase,
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
    val date: String
        get() = run {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = Date(timestamp * 1000)
            return dateFormat.format(date)
        }

    val time: String
        get() = run {
            val dateFormat = SimpleDateFormat("HH:mm")
            val date = Date(timestamp * 1000)
            return dateFormat.format(date)
        }

    val seatsState: String
        get() = "$currentSeats/$maxSeats"

    val bagsState: String
        get() = "$currentBags/$maxBags"


    fun getDateFormat(): String {
        val dateFormat = SimpleDateFormat("dd\nMMM")
        val date = Date(timestamp * 1000)
        return dateFormat.format(date)
    }

    fun getTimeFormat(): String {
        val dateFormat = SimpleDateFormat("hh:mm\naaa")
        val date = Date(timestamp * 1000)
        return dateFormat.format(date)
    }
}
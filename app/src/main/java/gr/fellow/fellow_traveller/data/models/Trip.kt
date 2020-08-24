package gr.fellow.fellow_traveller.data.models

import gr.fellow.fellow_traveller.framework.network.fellow.response.DestinationResponse
import java.text.SimpleDateFormat
import java.util.*

data class Trip(
    val id: Int,
    val destFrom: DestinationResponse,
    val destTo: DestinationResponse,
    val pickupPoint: DestinationResponse,
    val creatorUser: UserBase,
    val car: Car,
    val passengers: MutableList<Passenger>,
    val timestamp: Long,
    val hasPet: Boolean,
    val maxSeats: Int,
    val currentSeats: Int,
    val maxBags: Int,
    val currentBags: Int,
    val msg: String?,
    val price: Float
) {
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
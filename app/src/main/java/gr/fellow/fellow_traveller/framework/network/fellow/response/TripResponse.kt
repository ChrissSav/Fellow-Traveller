package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class TripResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("destFrom")
    val destFrom: DestinationResponse,
    @SerializedName("destTo")
    val destTo: DestinationResponse,
    @SerializedName("pickUpPoint")
    val pickupPoint: DestinationResponse,
    @SerializedName("creator")
    val creatorUser: UserBaseResponse,
    @SerializedName("car")
    val car: CarResponse,
    @SerializedName("passengers")
    val passengers: MutableList<PassengerResponse>,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("pet")
    val hasPet: Boolean,
    @SerializedName("max_seats")
    val maxSeats: Int,
    @SerializedName("current_seats")
    val currentSeats: Int,
    @SerializedName("max_bags")
    val maxBags: Int,
    @SerializedName("current_bags")
    val currentBags: Int,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float
) {
    fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd MM yyyy")
        val date = Date(timestamp * 1000)
        return dateFormat.format(date)
    }

    fun getTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val date = Date(timestamp * 1000)
        return dateFormat.format(date)
    }

    fun seatsState(): String {
        return "$currentSeats/$maxSeats"
    }

    fun bagsState(): String {
        return "$currentBags/$maxBags"
    }
}
package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName

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
    val msg: String,
    @SerializedName("price")
    val price: Float
)
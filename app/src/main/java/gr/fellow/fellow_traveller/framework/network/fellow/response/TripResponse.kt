package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName

data class TripResponse(
    val id: String,
    @SerializedName("destinationFrom")
    val destFrom: DestinationResponse,
    @SerializedName("destinationTo")
    val destTo: DestinationResponse,
    @SerializedName("creator")
    val creatorUser: UserBaseResponse,
    @SerializedName("car")
    val car: CarResponse,
    @SerializedName("pet")
    val hasPet: Boolean,
    val seats: Int,
    val bags: String,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float,
    @SerializedName("timestamp")
    val timestamp: Long,
    val passengers: MutableList<PassengerResponse>
)
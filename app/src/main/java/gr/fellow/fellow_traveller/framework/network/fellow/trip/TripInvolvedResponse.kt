package gr.fellow.fellow_traveller.framework.network.fellow.trip

import com.google.gson.annotations.SerializedName
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserCreatorResponse

data class TripInvolvedResponse(
    val id: String,
    @SerializedName("destinationFrom")
    val destFrom: DestinationResponse,
    @SerializedName("destinationTo")
    val destTo: DestinationResponse,
    val creator: UserCreatorResponse,
    val car: CarInfoResponse,
    @SerializedName("pet")
    val hasPet: Boolean,
    val seats: Int,
    val bags: Int,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float,
    val timestamp: Long,
    val passengers: MutableList<PassengerResponse>,
    val picture: String,
    val status: Int
)
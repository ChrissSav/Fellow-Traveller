package gr.fellow.fellow_traveller.framework.network.fellow.response.trip

import com.google.gson.annotations.SerializedName
import gr.fellow.fellow_traveller.framework.network.fellow.response.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserBaseResponse

data class TripInvolvedResponse(
    val id: String,
    @SerializedName("destinationFrom")
    val destFrom: DestinationResponse,
    @SerializedName("destinationTo")
    val destTo: DestinationResponse,
    val creator: UserBaseResponse,
    val car: CarInfoResponse,
    @SerializedName("pet")
    val hasPet: Boolean,
    val seats: Int,
    val bags: String,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float,
    val timestamp: Long,
    val passengers: MutableList<PassengerResponse>
)
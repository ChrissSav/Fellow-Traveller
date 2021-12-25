package gr.fellow.fellow_traveller.framework.network.fellow.trip

import com.google.gson.annotations.SerializedName
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserCreatorResponse

data class TripInvolvedResponse(
    val id: String,
    @SerializedName("destinationFrom")
    val destFrom: DestinationResponse,
    @SerializedName("destinationTo")
    val destTo: DestinationResponse,
    @SerializedName("destinationPickUp")
    val destPickUp: DestinationResponse,
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
    val status: Int,
) {


    fun getTripStatus(): TripStatus =
        when (status) {
            0 -> {
                TripStatus.ACTIVE
            }
            1 -> {
                TripStatus.PENDING
            }
            2 -> {
                TripStatus.COMPLETED
            }
            else -> {
                TripStatus.DELETED
            }
        }

    fun getBagsStatus(): BagsStatusType =
        when (bags) {
            0 -> {
                BagsStatusType.NONE
            }
            1 -> {
                BagsStatusType.LIMITED
            }
            else -> {
                BagsStatusType.LARGE
            }
        }
}
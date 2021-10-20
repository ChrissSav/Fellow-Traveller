package gr.fellow.fellow_traveller.framework.network.fellow.trip

import com.google.gson.annotations.SerializedName

data class TripCreateRequest(
    @SerializedName("destinationFrom")
    val destFrom: String,
    @SerializedName("destinationTo")
    val destTo: String,
    @SerializedName("destinationPickUp")
    val destPickUp: String,
    val carId: String,
    @SerializedName("pet")
    val hasPet: Boolean,
    val seats: Int,
    val bags: Int,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float,
    @SerializedName("timestamp")
    val timestamp: Long,
)
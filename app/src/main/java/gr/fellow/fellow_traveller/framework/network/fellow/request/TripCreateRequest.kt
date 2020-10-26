package gr.fellow.fellow_traveller.framework.network.fellow.request

import com.google.gson.annotations.SerializedName

data class TripCreateRequest(
    @SerializedName("destinationFrom")
    val destFrom: String,
    @SerializedName("destinationTo")
    val destTo: String,
    val carId: String,
    @SerializedName("pet")
    val hasPet: Boolean,
    val seats: Int,
    val bags: String,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float,
    @SerializedName("timestamp")
    val timestamp: Long
)
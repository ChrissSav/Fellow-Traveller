package gr.fellow.fellow_traveller.framework.network.fellow.request

import com.google.gson.annotations.SerializedName

data class TripCreateRequest(
    @SerializedName("dest_from")
    val destFrom: String,
    @SerializedName("dest_to")
    val destTo: String,
    @SerializedName("pickup_point")
    val pickupPoint: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("pet")
    val hasPet: Boolean,
    @SerializedName("max_seats")
    val maxSeats: Int,
    @SerializedName("max_bags")
    val maxBags: Int,
    @SerializedName("message")
    val msg: String?,
    @SerializedName("price")
    val price: Float,
    @SerializedName("car_id")
    val carId: Int
)
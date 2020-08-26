package gr.fellow.fellow_traveller.framework.network.fellow.request

import com.google.gson.annotations.SerializedName


data class BookTripRequest(
    @SerializedName("trip_id")
    val tripId: Int,
    val bags: Int,
    val pet: Boolean
)
package gr.fellow.fellow_traveller.framework.network.fellow.place


import com.google.gson.annotations.SerializedName
import gr.fellow.fellow_traveller.domain.trip.Administrative

data class PlaceDetailsResponse(
    @SerializedName("id")
    val placeId: String,
    val title: String,
    val latitude: Float,
    val longitude: Float,
    val administrative: Administrative
)

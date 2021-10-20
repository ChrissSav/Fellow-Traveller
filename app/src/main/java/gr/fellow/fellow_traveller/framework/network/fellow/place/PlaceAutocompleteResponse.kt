package gr.fellow.fellow_traveller.framework.network.fellow.place


import com.google.gson.annotations.SerializedName

data class PlaceAutocompleteResponse(
    @SerializedName("place_id")
    val placeId: String,
    val description: String,
)

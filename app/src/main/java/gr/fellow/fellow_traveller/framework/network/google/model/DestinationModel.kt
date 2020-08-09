package gr.fellow.fellow_traveller.framework.network.google.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class DestinationModel(
    val placeId: String,
    val title: String
)

@Parcelize
data class PlaceModel(
    val placeId: String,
    val title: String,
    var latitude: Float,
    var longitude: Float
) : Parcelable

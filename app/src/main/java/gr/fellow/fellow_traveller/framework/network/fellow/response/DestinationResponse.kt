package gr.fellow.fellow_traveller.framework.network.fellow.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DestinationResponse(
    val title: String,
    val latitude: Float,
    val longitude: Float
) : Parcelable
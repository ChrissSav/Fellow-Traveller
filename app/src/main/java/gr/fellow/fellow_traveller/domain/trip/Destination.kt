package gr.fellow.fellow_traveller.domain.trip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Destination(
    val placeId: String,
    val title: String,
    val latitude: Float?,
    val longitude: Float?,
    val administrative: Administrative?
) : Parcelable {

    val fullTitle
        get() = "$title, ${administrative?.title}"
}
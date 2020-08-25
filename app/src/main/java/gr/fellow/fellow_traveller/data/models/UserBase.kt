package gr.fellow.fellow_traveller.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserBase(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val picture: String?,
    val rate: Float,
    val reviews: Int
) : Parcelable {
    val fullName get() = "$firstName $lastName"
}
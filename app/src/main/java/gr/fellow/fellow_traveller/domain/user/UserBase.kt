package gr.fellow.fellow_traveller.domain.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserBase(
    val id: String,
    val firstName: String,
    val lastName: String,
    val picture: String?,
    val rate: Float,
    val reviews: Int
) : Parcelable {

    val fullName
        get() = "$firstName $lastName"

    val fullNameNewLine
        get() = "$firstName\n$lastName"

}
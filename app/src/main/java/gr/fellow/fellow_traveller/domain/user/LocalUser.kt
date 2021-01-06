package gr.fellow.fellow_traveller.domain.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalUser(
    val id: String,
    val firstName: String,
    val lastName: String,
    val rate: Float,
    val reviews: Int,
    val picture: String?,
    val aboutMe: String?,
    val email: String,
    val tripsOffers: Int,
    val tripsInvolved: Int,
) : Parcelable {
    val fullName get() = "$firstName $lastName"
}
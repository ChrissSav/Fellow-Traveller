package gr.fellow.fellow_traveller.domain.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Passenger(
    val user: UserBase,
    val bags: String,
    val pet: Boolean
) : Parcelable
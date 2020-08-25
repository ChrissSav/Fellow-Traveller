package gr.fellow.fellow_traveller.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Passenger(
    val user: UserBase,
    val bags: Int,
    val pet: Boolean
) : Parcelable
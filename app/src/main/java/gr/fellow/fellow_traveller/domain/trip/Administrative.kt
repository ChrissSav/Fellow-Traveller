package gr.fellow.fellow_traveller.domain.trip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Administrative(
    val image: String,
    val title: String
) : Parcelable
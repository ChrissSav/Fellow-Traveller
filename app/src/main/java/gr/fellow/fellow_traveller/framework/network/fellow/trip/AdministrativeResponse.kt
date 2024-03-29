package gr.fellow.fellow_traveller.framework.network.fellow.trip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdministrativeResponse(
    val image: String,
    val title: String
) : Parcelable
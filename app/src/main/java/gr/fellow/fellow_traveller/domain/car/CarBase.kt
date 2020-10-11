package gr.fellow.fellow_traveller.domain.car

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarBase(
    val brand: String,
    val model: String
) : Parcelable {

    val baseInfo: String
        get() = "$brand $model"

}
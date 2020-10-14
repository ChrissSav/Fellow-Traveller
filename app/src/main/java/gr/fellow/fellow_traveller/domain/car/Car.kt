package gr.fellow.fellow_traveller.domain.car

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val plate: String,
    val color: String
) : Parcelable {
    val baseInfo: String
        get() = "$brand $model"

    val fullInfo: String
        get() = "$brand $model | $plate | $color"

}
package gr.fellow.fellow_traveller.domain.car

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Car(
    val id: String,
    val brand: String,
    val model: String,
    val plate: String,
    val color: CarColor,
) : Parcelable {

    val baseInfo: String
        get() = "$brand $model"

    val fullInfo: String
        get() = "$brand $model | $plate | ${color.title}"

}


@Parcelize
data class CarColor(
    val title: String,
    val hex: String,
) : Parcelable

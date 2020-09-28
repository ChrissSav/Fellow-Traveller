package gr.fellow.fellow_traveller.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarBase(
    // val id : Int,
    val brand: String,
    val model: String
    //val plate : String,
    // val color : String
)/*{
    val description get() = "$brand $model | $plate"
}*/ : Parcelable
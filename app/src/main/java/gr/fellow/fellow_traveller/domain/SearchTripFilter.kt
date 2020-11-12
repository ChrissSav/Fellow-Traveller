package gr.fellow.fellow_traveller.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchTripFilter(
    var latitudeFrom: Float,
    var longitudeFrom: Float,
    var latitudeTo: Float,
    var longitudeTo: Float,
    var rangeFrom: Int? = null,
    var rangeTo: Int? = null,
    var timestampMin: Long? = null,
    var timestampMax: Long? = null,
    var seatsMin: Int? = null,
    var seatsMax: Int? = null,
    var priceMin: Int? = null,
    var priceMax: Int? = null,
    var pet: Boolean? = null
) : Parcelable {
    fun reset() {
        rangeFrom = null
        rangeTo = null
        timestampMin = null
        timestampMax = null
        seatsMin = null
        seatsMax = null
        priceMin = null
        priceMax = null
        pet = null
    }
}

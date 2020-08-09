package gr.fellow.fellow_traveller.domain

data class Filter(
    val latitudeFrom: Float,
    val longitudeFrom: Float,
    val latitudeTo: Float,
    val longitudeTo: Float,
    val rangeFrom: Int?,
    val rangeTo: Int?,
    val timestampMin: Int?,
    val timestampMax: Int?,
    val seatsMin: Int?,
    val seatsMax: Int?,
    val bagsMin: Int?,
    val bagsMax: Int?,
    val priceMin: Float?,
    val priceMax: Float?,
    val pet: Boolean?
)
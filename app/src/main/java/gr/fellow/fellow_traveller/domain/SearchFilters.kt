package gr.fellow.fellow_traveller.domain

data class SearchFilters(
    var latitudeFrom: Float,
    var longitudeFrom: Float,
    var latitudeTo: Float,
    var longitudeTo: Float,
    var rangeFrom: Int? = null,
    var rangeTo: Int? = null,
    var timestampMin: Int? = null,
    var timestampMax: Int? = null,
    var seatsMin: Int? = null,
    var seatsMax: Int? = null,
    var bagsMin: Int? = null,
    var bagsMax: Int? = null,
    var priceMin: Int? = null,
    var priceMax: Int? = null,
    var pet: Boolean? = null
) {
    fun reset() {
        rangeFrom = null
        rangeTo = null
        timestampMin = null
        timestampMax = null
        seatsMin = null
        seatsMax = null
        bagsMin = null
        bagsMax = null
        priceMin = null
        priceMax = null
        pet = null
    }
}

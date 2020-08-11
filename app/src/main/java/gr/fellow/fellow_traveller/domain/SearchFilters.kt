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
    var priceMin: Float? = null,
    var priceMax: Float? = null,
    var pet: Boolean? = null
)
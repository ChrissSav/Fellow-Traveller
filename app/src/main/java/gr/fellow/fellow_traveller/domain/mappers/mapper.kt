package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity


fun UserLoginResponse.toRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate, reviews, picture, aboutMe, phone, emailAddress
)

fun CarResponse.toCarEntity() = CarEntity(
    id, brand, model, plate, color
)


fun SearchFilters.toQuery(): String {
    var query = "latitude_from=${latitudeFrom}&longitude_from=${longitudeFrom}&latitude_to=${latitudeTo}&longitude_to=${longitudeTo}"
    if (rangeFrom != null)
        query += "&range_from${rangeFrom}"
    if (rangeTo != null)
        query += "&range_to${rangeTo}"
    if (timestampMin != null)
        query += "&timestamp_min${timestampMin}"
    if (timestampMax != null)
        query += "&timestamp_max${timestampMax}"
    if (seatsMin != null)
        query += "&seats_min${seatsMin}"
    if (seatsMax != null)
        query += "&seats_max${seatsMax}"
    if (bagsMin != null)
        query += "&bags_min${bagsMin}"
    if (bagsMax != null)
        query += "&bags_max${bagsMax}"
    if (priceMin != null)
        query += "&price_mix${priceMin}"
    if (priceMax != null)
        query += "&price_max${priceMax}"
    if (pet != null)
        query += "&pet${pet}"
    return query
}



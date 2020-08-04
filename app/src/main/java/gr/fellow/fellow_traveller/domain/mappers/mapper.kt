package gr.fellow.fellow_traveller.domain.mappers

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

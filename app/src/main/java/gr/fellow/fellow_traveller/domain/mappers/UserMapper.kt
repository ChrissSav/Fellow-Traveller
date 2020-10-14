package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserBaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

fun UserLoginResponse.toRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate, reviews, picture, aboutMe, phone, emailAddress
)


fun UserBaseResponse.toUserBase() = UserBase(
    id, FirstName, LastName, picture, rate, reviews
)
package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserBaseResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

fun UserAuthResponse.mapToRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate, reviews, picture, aboutMe, email, messengerLink
)


fun UserBaseResponse.toUserBase() = UserBase(
    id, firstName, lastName, picture, rate, reviews
)
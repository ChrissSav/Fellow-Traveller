package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserBaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserInfoResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

fun UserAuthResponse.mapToRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate, reviews, picture, aboutMe, email, messengerLink, tripsOffers, tripsInvolved
)


fun LocalUser.mapToRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate, reviews, picture, aboutMe, email, messengerLink, tripsOffers, tripsInvolved
)

fun UserBaseResponse.toUserBase() = UserBase(
    id, firstName, lastName, picture, rate, reviews
)

fun RegisteredUserEntity.mapToLocalUser(): LocalUser {
    return LocalUser(id, firstName, lastName, rate, reviews, picture, aboutMe, email, messengerLink, tripsOffers, tripsInvolved)
}


fun UserInfoResponse.mapToUserInfo() = UserInfo(
    id, firstName, lastName, rate, reviews, picture, messengerLink, aboutMe, tripsOffers, tripsInvolved
)
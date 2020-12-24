package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.domain.user.UserCreator
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserBaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserCreatorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserInfoResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.ui.extensions.round

fun UserAuthResponse.mapToRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate.round(1), reviews, picture, aboutMe, email, messengerLink, tripsOffers, tripsInvolved
)


fun LocalUser.mapToRegisteredUserEntity() = RegisteredUserEntity(
    id, firstName, lastName, rate.round(1), reviews, picture, aboutMe, email, messengerLink, tripsOffers, tripsInvolved
)

fun UserBaseResponse.mapToUserBase() = UserBase(
    id, firstName, lastName, picture, rate.round(1), reviews
)

fun UserCreatorResponse.mapToUserCreatorResponse() = UserCreator(
    id, firstName, lastName, picture, rate.round(1), reviews, messengerLink
)

fun UserCreator.mapToUserBase() = UserBase(
    id, firstName, lastName, picture, rate.round(1), reviews
)


fun RegisteredUserEntity.mapToLocalUser(): LocalUser {
    return LocalUser(id, firstName, lastName, rate.round(1), reviews, picture, aboutMe, email, messengerLink, tripsOffers, tripsInvolved)
}


fun UserInfoResponse.mapToUserInfo() = UserInfo(
    id, firstName, lastName, rate.round(1), reviews, picture, messengerLink, aboutMe, tripsOffers, tripsInvolved
)
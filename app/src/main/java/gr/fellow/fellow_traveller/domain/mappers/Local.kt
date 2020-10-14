package gr.fellow.fellow_traveller.domain.mappers

import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

fun RegisteredUserEntity.mapToLocalUser(): LocalUser {
    return LocalUser(id, firstName, lastName, rate, reviews, picture, aboutMe, email, messengerLink)
}


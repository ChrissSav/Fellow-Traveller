package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

interface LocalRepository {

    suspend fun registerUserAuth(userEntity: RegisteredUserEntity)
}
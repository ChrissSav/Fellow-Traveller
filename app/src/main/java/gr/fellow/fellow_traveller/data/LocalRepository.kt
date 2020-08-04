package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

interface LocalRepository {

    suspend fun registerUserAuth(userEntity: RegisteredUserEntity)

    suspend fun loadUserAuth() : RegisteredUserEntity

    suspend fun logoutUser()

    /**
     * Cars
     *
     **/
    suspend fun deleteCars()

    suspend fun deleteCarById(id :Int) : Int

    suspend fun getAllCars() : MutableList<CarEntity>


    suspend fun insertCar(car : CarEntity)

}
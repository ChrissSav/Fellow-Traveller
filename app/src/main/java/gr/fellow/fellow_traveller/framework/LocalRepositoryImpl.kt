package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.data.LocalRepository
import gr.fellow.fellow_traveller.room.dao.CarDao
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.utils.roomCall

class LocalRepositoryImpl(
    private val userAuthDao: UserAuthDao,
    private val carDao: CarDao
) : LocalRepository {


    override suspend fun registerUserAuth(userEntity: RegisteredUserEntity) =
        roomCall {
            userAuthDao.insertUser(userEntity)
        }


    override suspend fun loadUserAuth(): RegisteredUserEntity =
        roomCall {
            userAuthDao.getUserRegistered()
        }

    override suspend fun logoutUser() {
        roomCall {
            userAuthDao.deleteUser()
        }
    }

    /**
     * Cars
     **/

    override suspend fun deleteCars() =
        roomCall {
            carDao.deleteCars()
        }


    override suspend fun deleteCarById(id: Int) =
        roomCall {
            carDao.deleteCarById(id)
        }


    override suspend fun getAllCars(): MutableList<CarEntity> =
        roomCall {
            carDao.getCars()
        }

    override suspend fun insertCar(car: CarEntity) =
        roomCall {
            carDao.insertUser(car)
        }


}
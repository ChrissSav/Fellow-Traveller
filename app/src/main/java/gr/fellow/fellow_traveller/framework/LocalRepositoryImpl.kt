package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.data.LocalRepository
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

class LocalRepositoryImpl(
    private val userAuthDao: UserAuthDao
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


}
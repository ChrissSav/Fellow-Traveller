package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.data.LocalRepository
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

class LocalRepositoryImpl(
    private val userAuthDao: UserAuthDao
) : LocalRepository {


    override suspend fun registerUserAuth(userEntity: RegisteredUserEntity) {
        roomCall {
            userAuthDao.insertUser(userEntity)
        }
    }


}
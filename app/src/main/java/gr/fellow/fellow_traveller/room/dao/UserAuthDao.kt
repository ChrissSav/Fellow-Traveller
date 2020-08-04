package gr.fellow.fellow_traveller.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity


@Dao
interface UserAuthDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: RegisteredUserEntity)

    @Query("DELETE FROM user_auth")
    suspend fun deleteUser()

    @Query("SELECT * FROM user_auth")
    suspend fun getUserRegistered(): RegisteredUserEntity
}
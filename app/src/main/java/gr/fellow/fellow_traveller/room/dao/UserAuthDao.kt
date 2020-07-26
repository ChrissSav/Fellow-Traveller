package gr.fellow.fellow_traveller.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.room.entites.UserEntity


@Dao
interface UserAuthDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: RegisteredUserEntity)

    @Query("DELETE FROM user_auth")
    fun deleteUser()

    @Query("SELECT * FROM user_auth")
    fun getUserRegistered(): RegisteredUserEntity
}
package gr.fellow.fellow_traveller.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.fellow.fellow_traveller.room.entites.UserEntity



@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM users")
    fun deleteUser()

    @Query("SELECT * FROM users")
    fun getUsers(): UserEntity
}
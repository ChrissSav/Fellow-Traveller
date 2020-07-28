package gr.fellow.fellow_traveller.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.fellow.fellow_traveller.room.entites.PassengerEntity



@Dao
interface PassengerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(passengerEntity: PassengerEntity)

    @Query("DELETE FROM passengers")
    suspend fun deleteUser()

    @Query("SELECT * FROM passengers")
    suspend fun getUsers(): PassengerEntity
}
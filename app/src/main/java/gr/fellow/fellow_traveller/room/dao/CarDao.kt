package gr.fellow.fellow_traveller.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gr.fellow.fellow_traveller.room.entites.CarEntity


@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(carEntity: CarEntity)

    @Query("DELETE FROM car")
    suspend fun deleteCars(): Int

    @Query("DELETE FROM car where id = :id")
    suspend fun deleteCarById(id: String): Int

    @Query("SELECT * FROM car")
    suspend fun getCars(): MutableList<CarEntity>

}
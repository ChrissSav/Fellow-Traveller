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

    @Query("DELETE FROM cars")
    suspend fun deleteCars()

    @Query("DELETE FROM cars where id = :id")
    suspend fun deleteCarById(id: Int)

    @Query("SELECT * FROM cars")
    suspend fun getCars(): MutableList<CarEntity>

}
package gr.fellow.fellow_traveller.room

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.fellow.fellow_traveller.room.dao.CarDao
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.dao.PassengerDao
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.room.entites.PassengerEntity

@Database(
    entities = [RegisteredUserEntity::class, PassengerEntity::class, CarEntity::class],
    version = 1
)
abstract class FellowDatabase : RoomDatabase() {

    abstract fun userAuthDao(): UserAuthDao

    abstract fun useDao(): PassengerDao

    abstract fun carDao(): CarDao

}
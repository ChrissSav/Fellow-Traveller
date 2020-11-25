package gr.fellow.fellow_traveller.room

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

@Database(
    entities = [RegisteredUserEntity::class],
    version = 1
)
abstract class FellowDatabase : RoomDatabase() {

    abstract fun userAuthDao(): UserAuthDao

}
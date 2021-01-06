package gr.fellow.fellow_traveller.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity


@Database(
    entities = [RegisteredUserEntity::class],
    version = 2
)
abstract class FellowDatabase : RoomDatabase() {

    abstract fun userAuthDao(): UserAuthDao

}


val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Copy the data
        database.execSQL("CREATE TABLE user_auth_new (id TEXT NOT NULL, first_name TEXT NOT NULL, last_name TEXT NOT NULL, rate REAL NOT NULL, reviews INTEGER NOT NULL, picture TEXT, about_me TEXT, email TEXT NOT NULL, tripsOffers INTEGER NOT NULL, tripsInvolved INTEGER NOT NULL, PRIMARY KEY(id))")
        database.execSQL("INSERT INTO user_auth_new (id,first_name,last_name,rate,reviews,picture,about_me,email,tripsOffers,tripsInvolved) " +
                "SELECT id,first_name,last_name,rate,reviews,picture,about_me,email,tripsOffers,tripsInvolved FROM user_auth")
        database.execSQL("DROP TABLE user_auth")
        database.execSQL("ALTER TABLE user_auth_new RENAME TO user_auth");

    }
}
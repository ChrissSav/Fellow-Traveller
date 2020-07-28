package gr.fellow.fellow_traveller.room.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "passengers")
data class PassengerEntity (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    @ColumnInfo(name = "first_name")
    val firstName : String,
    @ColumnInfo(name = "last_name")
    val lastName : String,
    val rate : Float,
    val reviews : Int,
    val picture : String?
)
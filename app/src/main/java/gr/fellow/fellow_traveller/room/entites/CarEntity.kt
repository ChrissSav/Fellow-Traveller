package gr.fellow.fellow_traveller.room.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cars")
data class CarEntity (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val brand : String,
    val model : String,
    val plate : String,
    val color : String
)
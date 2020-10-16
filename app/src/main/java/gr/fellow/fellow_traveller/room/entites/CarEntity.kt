package gr.fellow.fellow_traveller.room.entites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "car")
data class CarEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val brand: String,
    val model: String,
    val plate: String,
    val color: String
)
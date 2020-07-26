package gr.fellow.fellow_traveller.room.entites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "destinations")
data class DestinationsEntity(

    @PrimaryKey(autoGenerate = false)
    val place_id: String,
    val title: String,
    val latitude: Float,
    val longitude: Float
)
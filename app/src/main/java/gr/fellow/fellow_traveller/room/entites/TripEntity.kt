package gr.fellow.fellow_traveller.room.entites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @Embedded val destFrom: DestinationsEntity,
    @Embedded val destTo: DestinationsEntity,
    @Embedded val pickUpPoint: DestinationsEntity,
    @Embedded val car: CarEntity,
    @Embedded val creator: String,
    val pet: Boolean,
    val price: Float,
    val maxSeats: Int,
    val maxBags: Int,
    val message: String,
    val timestamp: Long,
    val currentSeats: Int,
    val currentBags: Int,
    val passengers: String,
    val active: Boolean
)
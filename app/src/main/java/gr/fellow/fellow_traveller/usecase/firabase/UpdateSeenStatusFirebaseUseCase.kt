package gr.fellow.fellow_traveller.usecase.firabase

import gr.fellow.fellow_traveller.domain.FellowDataSource
import kotlin.collections.set


class UpdateSeenStatusFirebaseUseCase(
    private val dataSource: FellowDataSource,
) {
    suspend operator fun invoke(tripId: String, userId: String) {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["seen"] = true

        dataSource.updateSeenStatus(hashMap, tripId, userId)
    }


}
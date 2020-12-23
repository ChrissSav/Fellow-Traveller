package gr.fellow.fellow_traveller.usecase.firabase

import gr.fellow.fellow_traveller.domain.FellowDataSource

class CreateOrEnterConversationFirebaseUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(myId: String, creatorId: String, tripId: String, tripName: String) {


        dataSource.createOrEnterConversation(myId, creatorId, tripId, tripName)
    }


}
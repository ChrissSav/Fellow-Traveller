package gr.fellow.fellow_traveller.usecase.firabase

import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteConversationFirebaseUseCase(
    private val dataSource: FellowDataSource,
) {
    suspend operator fun invoke(userId: String, tripId: String) {

        dataSource.deleteConversation(userId, tripId)
    }


}
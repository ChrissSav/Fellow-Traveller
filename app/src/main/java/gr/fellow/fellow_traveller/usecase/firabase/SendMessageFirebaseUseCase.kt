package gr.fellow.fellow_traveller.usecase.firabase

import gr.fellow.fellow_traveller.domain.FellowDataSource
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

class SendMessageFirebaseUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(userId: String, tripId: String, textMessage: String, senderName: String, messageType: Int, participantsList: ArrayList<String>) {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["senderId"] = userId
        hashMap["tripId"] = tripId
        hashMap["text"] = textMessage
        //Timestamp take value from ServerFirabase in FirebaseRepositoryImpl
        hashMap["senderName"] = senderName
        hashMap["messageType"] = messageType


        dataSource.sendFirebaseMessage(hashMap, participantsList)
    }


}
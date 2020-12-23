package gr.fellow.fellow_traveller.data

import android.net.Uri


interface FirebaseRepository {

    suspend fun uploadImage(uri: Uri, userId: String): String

    suspend fun sendMessage(hashMap: HashMap<String, Any>)

    suspend fun createOrEnterConversation(myId: String, creatorId: String, tripId: String, tripName: String)


}
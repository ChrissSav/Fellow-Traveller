package gr.fellow.fellow_traveller.data

import android.net.Uri
import gr.fellow.fellow_traveller.domain.chat.ChatMessage


interface FirebaseRepository {

    suspend fun uploadImage(uri: Uri, userId: String): String

    suspend fun sendMessage(hashMap: HashMap<String, Any>)



}
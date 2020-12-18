package gr.fellow.fellow_traveller.data

import android.net.Uri


interface FirebaseRepository {

    suspend fun uploadImage(uri: Uri, userId: String): String

}
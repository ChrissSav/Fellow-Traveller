package gr.fellow.fellow_traveller.framework


import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import gr.fellow.fellow_traveller.data.FirebaseRepository
import gr.fellow.fellow_traveller.utils.firebaseCall
import kotlinx.coroutines.tasks.await


class FirebaseRepositoryImpl(
    private val firebaseStorage: FirebaseStorage
) : FirebaseRepository {


    override suspend fun uploadImage(uri: Uri, userId: String): String =
        firebaseCall {
            val storageRef = firebaseStorage.getReference("profile_images")
            val fileReference = storageRef.child("user-$userId")
            fileReference.putFile(uri).await()
            fileReference.downloadUrl.await().toString()
        }

}
package gr.fellow.fellow_traveller.framework


import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import gr.fellow.fellow_traveller.data.FirebaseRepository
import gr.fellow.fellow_traveller.utils.firebaseCall
import kotlinx.coroutines.tasks.await


class FirebaseRepositoryImpl(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseDatabase: FirebaseDatabase

) : FirebaseRepository {



    override suspend fun uploadImage(uri: Uri, userId: String): String =
        firebaseCall {
            val storageRef = firebaseStorage.getReference("profile_images")
            val fileReference = storageRef.child("user-$userId")
            fileReference.putFile(uri).await()
            fileReference.downloadUrl.await().toString()
        }

    override suspend fun sendMessage(hashMap: HashMap<String, Any>) {
        firebaseCall {

            val tripId = hashMap["tripId"].toString()
            hashMap["timestamp"] = ServerValue.TIMESTAMP
            val referenceMessage = firebaseDatabase.reference.child("Messages").child(tripId)
            referenceMessage.push().setValue(hashMap).await()
        }
    }

    override suspend fun createOrEnterConversation(myId: String, creatorId: String, tripId: String, tripName: String) {
        firebaseCall {
            val tripsDatabase = firebaseDatabase.reference.child("TripsAndParticipants").child(tripId)
                .child(myId)

            val tripsMap: HashMap<String, Any> = HashMap()
            tripsMap["timestamp"] = ServerValue.TIMESTAMP
            tripsMap["seen"] = true
            tripsMap["tripId"] = tripId
            tripsMap["tripName"] = tripName


            tripsDatabase.setValue(tripsMap)

        }
    }


}
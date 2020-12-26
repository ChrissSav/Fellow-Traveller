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
            //Assign to passenger a new conversation
            val passengerConversation = firebaseDatabase.reference.child("UserTrips").child(myId).child(tripId)
            //Assign to creator a new conversation
            val creatorConversation = firebaseDatabase.reference.child("UserTrips").child(creatorId).child(tripId)
            //Assign to to trip a new passenger member
            val passengerToTrip = firebaseDatabase.reference.child("TripsAndParticipants").child(tripId).child(myId)
            //Assign to to trip the creator as member
            val creatorToTrip = firebaseDatabase.reference.child("TripsAndParticipants").child(tripId).child(creatorId)

            val tripsMap: HashMap<String, Any> = HashMap()
            tripsMap["timestamp"] = ServerValue.TIMESTAMP
            tripsMap["seen"] = true
            tripsMap["tripId"] = tripId
            tripsMap["tripName"] = tripName

            //Update creator and passenger conversation
            passengerConversation.setValue(tripsMap)
            creatorConversation.setValue(tripsMap)

            //Create Hash Maps, to assign the passenger and the creator to the trip's member
            val passengerMemberMap: HashMap<String, Any> = HashMap()
            passengerMemberMap["userId"] = myId

            val creatorMemberMap: HashMap<String, Any> = HashMap()
            creatorMemberMap["userId"] = creatorId


            passengerToTrip.setValue(passengerMemberMap)
            creatorToTrip.setValue(creatorMemberMap)

        }
    }


}
package gr.fellow.fellow_traveller.framework


import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import gr.fellow.fellow_traveller.data.FirebaseRepository
import gr.fellow.fellow_traveller.utils.firebaseCall
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.forEach
import kotlin.collections.set


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

    override suspend fun sendMessage(hashMap: HashMap<String, Any>, participantsList: ArrayList<String>) {
        firebaseCall {

            val tripId = hashMap["tripId"].toString()
            hashMap["timestamp"] = ServerValue.TIMESTAMP
            val referenceMessage = firebaseDatabase.reference.child("Messages").child(tripId)
            referenceMessage.push().setValue(hashMap).await()

            updateParticipantsStatus(hashMap, participantsList)
        }
    }

    override suspend fun createOrEnterConversation(myId: String, creatorId: String, tripId: String, tripName: String, picture: String) {
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
            tripsMap["description"] = "default"
            tripsMap["image"] = picture

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

    private suspend fun updateParticipantsStatus(hashMap: HashMap<String, Any>, participantsList: ArrayList<String>) {

        val textMessage = hashMap["text"].toString()
        val tripId = hashMap["tripId"].toString()

        val updateMap: HashMap<String, Any> = HashMap()
        updateMap["timestamp"] = ServerValue.TIMESTAMP
        updateMap["description"] = textMessage
        updateMap["seen"] = false


        //Update conversation's info, of all participants (including mine)
        participantsList.forEach {
            val reference = firebaseDatabase.reference.child("UserTrips").child(it).child(tripId)
            reference.updateChildren(updateMap).await()
        }

        //Update my hashMap as seen true
        val reference = firebaseDatabase.reference.child("UserTrips").child(hashMap["senderId"].toString()).child(tripId)
        val updateMyMap: HashMap<String, Any> = HashMap()
        updateMyMap["seen"] = true
        reference.updateChildren(updateMyMap).await()


    }

}
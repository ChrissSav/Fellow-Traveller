package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseIdService : FirebaseMessagingService() {
    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        //Get users id
        var myId = "sdags"
        val refreshToken: String? = FirebaseInstanceId.getInstance().token
        //refreshToken = FirebaseMessaging.getInstance().token.toString()
        if (myId != null)
            refreshToken?.let { updateToken(it) }
    }

    fun updateToken(refreshToken: String) {
        //Get users id
        var myId = "sdags"

        val reference = firebaseDatabase.getReference("Tokens")
        val token = Token(refreshToken)
        reference.child(myId).setValue(token)

    }

}
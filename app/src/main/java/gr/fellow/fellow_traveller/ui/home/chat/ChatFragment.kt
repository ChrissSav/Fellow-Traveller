package gr.fellow.fellow_traveller.ui.home.chat

import android.util.Log
import androidx.annotation.NonNull
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChatBinding
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.service.NotificationJobService.Companion.TAG
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.MessagesAdapter
import gr.fellow.fellow_traveller.ui.home.chat.chat_notifications.NotificationData
import gr.fellow.fellow_traveller.ui.home.chat.chat_notifications.PushNotification
import gr.fellow.fellow_traveller.ui.home.chat.chat_notifications.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val TOPIC = "/topics/myTopic"
@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private val messagesList = mutableListOf<ChatMessage>()
    private val participantsIdList: ArrayList<String> = ArrayList()

    private var participantsInfo = mutableListOf<UserInfo>()

    private lateinit var messageQuery: Query
    private lateinit var messageChildEventListener: ChildEventListener

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase


    override fun getViewBinding(): FragmentChatBinding =
        FragmentChatBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.messagesList.observe(viewLifecycleOwner, {
            messagesList.add(it)
            (binding.chatRecyclerView.adapter as MessagesAdapter).notifyItemInserted(messagesList.size - 1)
            binding.chatRecyclerView.scrollToPosition(messagesList.size - 1)
        })

        viewModel.listOfParticipants.observe(viewLifecycleOwner, {
            participantsInfo = it
            (binding.chatRecyclerView.adapter as MessagesAdapter).notifyDataSetChanged()
            readMessages(args.conversationItem.tripId)
        })

    }

    override fun setUpViews() {
        messagesList.clear()

        viewModel.updateSeenStatus(args.conversationItem.tripId)
        binding.chatHeader.text = args.conversationItem.tripName

        binding.chatSendButton.isEnabled = false

        //ar apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService::class.java)

        binding.chatRecyclerView.adapter = MessagesAdapter(messagesList, viewModel.user.value?.id.toString(), participantsInfo)

        getAllParticipantsId(args.conversationItem.tripId)


//        messagesList.add(Message("uyugfafkdgskjgk234kfdkf", "Καλημέραααα", "fsj'dghsdfgsgjlg.zsd", 1608218126, "John", "default"))

        //Parse elements in adapter and on click listener
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.chatSendButton.setOnClickListener {
            //MessageType: When 0: message, When 1: event
            viewModel.sendFirebaseMessage(binding.messageEditText.text.toString(), args.conversationItem.tripId, 0, participantsIdList)
            PushNotification(
                NotificationData("Νέο μήνυμα", binding.messageEditText.text.toString()),
                TOPIC
            ).also { sendNotification(it) }
            binding.messageEditText.text = null

        }

    }

    private fun getAllParticipantsId(tripId: String) {
        participantsIdList.clear()
        val reference2 = firebaseDatabase.getReference("TripsAndParticipants").child(tripId)
        reference2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val aId = snapshot.child("userId").getValue(String::class.java)!!
                    participantsIdList.add(aId)
                }
                binding.chatSendButton.isEnabled = true
                viewModel.getParticipants(participantsIdList)
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {}
        })
    }

    private fun readMessages(tripId: String) {
        val reference = firebaseDatabase.getReference("Messages").child(tripId)
        messageQuery = reference.limitToLast(100)
        messageChildEventListener = object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)

                dataSnapshot.getValue(ChatMessage::class.java)?.let {
                    viewModel.loadChatMessage(it)
                }

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        messageQuery.addChildEventListener(messageChildEventListener)

    }


//    private fun sendNotification(receiver: String, username: String, message: String) {
//        val tokens = firebaseDatabase.getReference("Tokens")
//        val query = tokens.orderByKey().equalTo(receiver)
//
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val token: Token? = snapshot.getValue(Token::class.java)
//                    val data = Data(args.conversationItem.tripId, R.drawable.ic_btn_speak_now, username + ": " + message, "Νέο μήνυμα", receiver)
//                    val sender = Sender(data, token.getToken())
//                    apiService.sendNotification(sender)
//                        .enqueue(object : Callback<MyResponse?>() {
//                            fun onResponse(call: Call<MyResponse?>?, response: Response<MyResponse?>) {
//                                if (response.code() === 200) {
//                                    if (response.body()?.success !== 1) {
//                                        //Toast.makeText(ChatConversationActivity.this, "Απέτυχε", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//
//                            fun onFailure(call: Call<MyResponse?>?, t: Throwable?) {}
//                        })
//                }
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        }
//        query.addValueEventListener(postListener)
//    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    //Log.e(TAG, "Response: ${Gson().toJson(response)}")
                } else {
                    // Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                val TAG = "ChatFragment"
                Log.e(TAG, e.toString())
            }
        }


    override fun onDestroy() {
        if (this::messageQuery.isInitialized) {
            runBlocking {
                messageQuery.removeEventListener(messageChildEventListener)
            }
        }
        super.onDestroy()


    }

}
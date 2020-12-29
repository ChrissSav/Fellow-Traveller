package gr.fellow.fellow_traveller.ui.home.chat

import android.util.Log
import androidx.annotation.NonNull
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChatBinding
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.service.NotificationJobService.Companion.TAG
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.MessagesAdapter
import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private val messagesList = mutableListOf<ChatMessage>()
    private val participantsIdList: ArrayList<String> = ArrayList()

    private var participantsInfo = mutableListOf<UserInfo>()


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
        })

    }

    override fun setUpViews() {
        messagesList.clear()


        binding.chatHeader.text = args.conversationItem.tripName
        binding.chatRecyclerView.adapter = MessagesAdapter(messagesList, viewModel.user.value?.id.toString(), participantsInfo)
        binding.chatSendButton.isEnabled = false

        getAllParticipantsId(args.conversationItem.tripId)

        readMessages(args.conversationItem.tripId)
//        messagesList.add(Message("uyugfafkdgskjgk234kfdkf", "Καλημέραααα", "fsj'dghsdfgsgjlg.zsd", 1608218126, "John", "default"))

        //Parse elements in adapter and on click listener

        binding.chatSendButton.setOnClickListener {
            //MessageType: When 0: message, When 1: event
            viewModel.sendFirebaseMessage(binding.messageEditText.text.toString(), args.conversationItem.tripId, 0, participantsIdList)
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
        val messageQuery: Query = reference.limitToLast(100)
        val childEventListener = object : ChildEventListener {

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
        messageQuery.addChildEventListener(childEventListener)

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        runBlocking{
//            reference.removeValue().await()
//        }
//
//    }
}
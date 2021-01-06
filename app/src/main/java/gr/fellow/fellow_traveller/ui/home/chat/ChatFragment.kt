package gr.fellow.fellow_traveller.ui.home.chat

import android.util.Log
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.discord.panels.OverlappingPanelsLayout
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChatBinding
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.service.NotificationJobService.Companion.TAG
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.ChatPassengersAdapter
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

    private lateinit var tripInvolved: TripInvolved

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase


    override fun getViewBinding(): FragmentChatBinding =
        FragmentChatBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.messagesList.observe(viewLifecycleOwner, {
            viewModel.loadMessages.value = false
            messagesList.add(it)
            (binding.chat.chatRecyclerView.adapter as MessagesAdapter).notifyItemInserted(messagesList.size - 1)
            binding.chat.chatRecyclerView.scrollToPosition(messagesList.size - 1)
        })

        viewModel.listOfParticipants.observe(viewLifecycleOwner, {
            participantsInfo.clear()
            participantsInfo.addAll(it)
            (binding.chat.chatRecyclerView.adapter as MessagesAdapter).notifyDataSetChanged()
            readMessages(args.conversationItem.tripId)
        })

        viewModel.tripInfo.observe(viewLifecycleOwner, {
            //Load end panel with tripInvolved info
            tripInvolved = it
            createToast(it.status.textInt.toString())
            with(binding.info) {
                from.text = it.destFrom.title
                to.text = it.destTo.title
                day.text = it.date

                driverName.text = it.creatorUser.fullName
                driverImage.loadImageFromUrl(it.creatorUser.picture)
                chatInfoPassengersRecyclerView.adapter = ChatPassengersAdapter(it.passengers)
            }
            binding.chat.chatCreatorName.setTextHtml(binding.chat.chatCreatorName.context.getString(R.string.conversation_creator_name, it.creatorUser.fullName))

        })

        viewModel.loadMessages.observe(viewLifecycleOwner, {

            if (it) {
                Log.i("kjhgrgrg", "rpogjirugreg")
                binding.chat.chatShimmer.startShimmerWithVisibility()
                //binding.messagesSection.visibility = View.INVISIBLE

            } else {
                binding.chat.chatShimmer.stopShimmerWithVisibility()
            }
        })

    }

    override fun setUpViews() {

        binding.overlappingPanels.setStartPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)

        viewModel.getTripById(args.conversationItem.tripId)

        messagesList.clear()

        viewModel.updateSeenStatus(args.conversationItem.tripId)
        binding.chat.chatHeader.text = args.conversationItem.tripName

        binding.chat.chatSendButton.isEnabled = false



        binding.chat.chatRecyclerView.adapter = MessagesAdapter(messagesList, viewModel.user.value?.id.toString(), participantsInfo)

        getAllParticipantsId(args.conversationItem.tripId)


        //Parse elements in adapter and on click listener
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.chat.chatSendButton.setOnClickListener {
            if (!binding.chat.messageEditText.text.toString().trim().isNullOrEmpty()) {
                //MessageType: When 0: message, When 1: event
                viewModel.sendFirebaseMessage(binding.chat.messageEditText.text.toString().trim(), args.conversationItem.tripId, 0, participantsIdList)
                PushNotification(
                    NotificationData("Νέο μήνυμα", binding.chat.messageEditText.text.toString()),
                    TOPIC
                ).also { sendNotification(it) }
                binding.chat.messageEditText.text = null
            }
        }


        binding.chat.chatInfoButton.setOnClickListener {
            binding.overlappingPanels.openEndPanel()
        }

        binding.info.tripButton.setOnClickListener {
            if (tripInvolved != null) {
                findNavController()?.navigate(
                    R.id.action_chatFragment_to_tripInvolvedDetailsSecondFragment,
                    bundleOf(
                        "tripId" to args.conversationItem.tripId,
                        "reload" to false,
                        "history" to (tripInvolved.status == TripStatus.ACTIVE || tripInvolved.status == TripStatus.PENDING),
                        "creator" to (tripInvolved.creatorUser.id == viewModel.user.value?.id.toString()))
                )
            }
        }

    }

    private fun getAllParticipantsId(tripId: String) {
        viewModel.loadMessages.value = true
        participantsIdList.clear()
        val reference2 = firebaseDatabase.getReference("TripsAndParticipants").child(tripId)
        reference2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val aId = snapshot.child("userId").getValue(String::class.java)!!
                    participantsIdList.add(aId)
                }
                binding.chat.chatSendButton.isEnabled = true
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
                    viewModel.loadMessages.value = true
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


    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {

                } else {

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
        //When we exit the frag we update the status as seen
        viewModel.updateSeenStatus(args.conversationItem.tripId)

        super.onDestroy()


    }

}
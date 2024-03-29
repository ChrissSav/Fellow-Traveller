package gr.fellow.fellow_traveller.ui.home.chat

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChatBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.chat.ChatMessage
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.Passenger
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startActivityWithBundle
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.chat.chat_notifications.PushNotification
import gr.fellow.fellow_traveller.ui.home.chat.chat_notifications.RetrofitInstance
import gr.fellow.fellow_traveller.ui.user.UserProfileDetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


const val TOPIC = "/topics/myTopic"

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private val messagesList = mutableListOf<ChatMessage>()
    private var participantsIdList = mutableListOf<String>()

    private var participantsInfo = mutableListOf<UserInfo>()

    private var updateStatusWhenExit: Boolean = true
    private var flag: Boolean = true

    private lateinit var messageQuery: Query
    private lateinit var participantsReference: DatabaseReference
    private lateinit var messageChildEventListener: ChildEventListener
    private lateinit var participantsListener: ValueEventListener

    private var tripInvolved: TripInvolved? = null

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase


    override fun getViewBinding(): FragmentChatBinding =
        FragmentChatBinding.inflate(layoutInflater)

    override fun setUpObservers() {

//        viewModel.messagesList.observe(viewLifecycleOwner) {
//            viewModel.loadMessages.value = false
//            messagesList.add(it)
//            (binding.chat.chatRecyclerView.adapter as MessagesAdapter).notifyItemInserted(messagesList.size - 1)
//            binding.chat.chatRecyclerView.scrollToPosition(messagesList.size - 1)
//        }
//
//        viewModel.listOfParticipants.observe(viewLifecycleOwner) {
//            participantsInfo.clear()
//            viewModel.getTripById(args.conversationItem.tripId)
//            participantsInfo.addAll(it)
//            binding.chat.chatSendButton.isEnabled = true
//
//
//
//            (binding.chat.chatRecyclerView.adapter as MessagesAdapter).notifyDataSetChanged()
//            if (updateStatusWhenExit) {
//                readMessages(args.conversationItem.tripId)
//                updateStatusWhenExit = false
//            }
//
//
//        }
//
//        viewModel.tripInfo.observe(viewLifecycleOwner) { trip ->
//            //Load end panel with tripInvolved info
//            tripInvolved = trip
//
//            //if trip is null then the trip is deleted, so display the appropriate layout sections
//            if (tripInvolved == null) {
//                binding.overlappingPanels.setEndPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)
//                binding.chat.deletedConversationSection.visibility = View.VISIBLE
//                binding.chat.sendMessageSection.visibility = View.GONE
//                binding.chat.chatInfoButton.visibility = View.GONE
//            }
//
//            trip?.let {
//                binding.overlappingPanels.setEndPanelLockState(OverlappingPanelsLayout.LockState.UNLOCKED)
//
//                with(binding.info) {
//                    from.text = it.destFrom.title
//                    to.text = it.destTo.title
//                    day.text = it.date
//                    time.text = it.time
//
//                    driverName.text = it.creatorUser.fullName
//                    driverImage.loadImageFromUrl(it.creatorUser.picture)
//                    chatInfoPassengersRecyclerView.adapter = ChatPassengersAdapter(it.passengers, this@ChatFragment::onPassengerClick)
//                }
//
//                if (it.creatorUser.id == viewModel.user.value?.id) {
//                    binding.info.deleteTextView.text = getString(R.string.delete)
//                } else {
//                    binding.info.deleteTextView.text = getString(R.string.leave)
//                }
//
//                if (it.passengers.isNotEmpty()) {
//                    binding.info.passengersSection.visibility = View.GONE
//                } else {
//                    binding.info.passengersSection.visibility = View.VISIBLE
//                }
//
//                /** When we implement the Web Sockets we will use this updated value for creator's user name **/
//                //binding.chat.chatCreatorName.setTextHtml(binding.chat.chatCreatorName.context.getString(R.string.conversation_creator_name, it.creatorUser.fullName))
//            }
//
//
//        }
//
//        viewModel.loadMessages.observe(viewLifecycleOwner) {
//
//            if (it) {
//                binding.chat.chatShimmer.startShimmerWithVisibility()
//            } else {
//                binding.chat.chatShimmer.stopShimmerWithVisibility()
//            }
//        }

    }

    override fun onPause() {
        super.onPause()
        updateStatusWhenExit = true
    }

    override fun setUpViews() {
//
//        binding.overlappingPanels.setStartPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)
//        binding.overlappingPanels.setEndPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)
//
//        /** Now we get the value from firebase,which is not updated.. Later we gonna use it from tripInvolve **/
//        if (!args.conversationItem.creatorId.isNullOrEmpty()) {
//            if (viewModel.user.value?.id.toString() == args.conversationItem.creatorId)
//                binding.chat.chatCreatorName.text = getString(R.string.conversation_from_you)
//            else
//                binding.chat.chatCreatorName.setTextHtml(binding.chat.chatCreatorName.context.getString(R.string.conversation_creator_name, args.conversationItem.creatorName))
//        }
//
//        viewModel.getTripById(args.conversationItem.tripId)
//
//        messagesList.clear()
//
//        viewModel.updateSeenStatus(args.conversationItem.tripId)
//        binding.chat.chatHeader.text = args.conversationItem.tripName
//
//        binding.chat.chatSendButton.isEnabled = false
//
//
//
//        binding.chat.chatRecyclerView.adapter = MessagesAdapter(messagesList, viewModel.user.value?.id.toString(), participantsInfo)
//
//        getAllParticipantsId(args.conversationItem.tripId)
//
//
//        //Parse elements in adapter and on click listener
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
//
//        binding.chat.chatSendButton.setOnClickListener {
//            if (!binding.chat.messageEditText.text.toString().trim().isNullOrEmpty()) {
//                //MessageType: When 0: message, When 1: event
//                viewModel.sendFirebaseMessage(binding.chat.messageEditText.text.toString().trim(), args.conversationItem.tripId, 0, participantsIdList)
//                PushNotification(
//                    NotificationData("Νέο μήνυμα", binding.chat.messageEditText.text.toString()),
//                    TOPIC
//                ).also { sendNotification(it) }
//                binding.chat.messageEditText.text = null
//            }
//        }
//
//
//        binding.chat.chatInfoButton.setOnClickListener {
//            binding.overlappingPanels.openEndPanel()
//        }
//
//        binding.info.tripButton.setOnClickListener {
//            tripInvolved?.let { trip ->
//                findNavController()?.navigate(
//                    R.id.action_chatFragment_to_tripInvolvedDetailsSecondFragment,
//                    bundleOf(
//                        "tripId" to args.conversationItem.tripId,
//                        "reload" to false,
//                        "history" to (trip.status == TripStatus.COMPLETED),
//                        "creator" to (trip.creatorUser.id == viewModel.user.value?.id.toString()))
//                )
//            }
//        }
//
//        binding.info.driverSection.setOnClickListener {
//            if (tripInvolved?.creatorUser?.id != viewModel.user.value?.id.toString()) {
//                activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to tripInvolved?.creatorUser?.id))
//            } else {
//                findNavController()?.navigate(R.id.action_chatFragment_to_destination_info)
//            }
//        }
//        binding.chat.chatBackButton.setOnClickListener {
//            onBackPressed()
//        }
//        binding.info.picturesButton.setOnClickListener {
//            createToast(getString(R.string.no_pictures))
//        }
//        binding.info.exitButton.setOnClickListener {
//            if (viewModel.user.value?.id == tripInvolved?.creatorUser?.id)
//                ExitCustomDialog(activity as HomeActivity, this::exitCustomDialogAnswerType, getString(R.string.delete_this_trip), 1).show(parentFragmentManager, "exitCustomDialog")
//            else
//                ExitCustomDialog(activity as HomeActivity, this::exitCustomDialogAnswerType, getString(R.string.exit_from_trip), 1).show(parentFragmentManager, "exitCustomDialog")
//        }
//
//        binding.chat.chatRecyclerView.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//
//            //When we don't have data on the list, it will cause crash if we scroll to position
//
//            if (bottom < oldBottom) {
//                binding.chat.chatRecyclerView.postDelayed(Runnable {
//                    try {
//                        binding.chat.chatRecyclerView.smoothScrollToPosition(messagesList.size - 1)
//                    } catch (e: IllegalArgumentException) {
//                        Log.e("SCROLL_TO_POSITION", e.toString())
//                    }
//                }, 100)
//            }
//
//        }

    }

    private fun getAllParticipantsId(tripId: String) {
//        viewModel.loadMessages.value = true
//        participantsIdList.clear()
//        participantsReference = firebaseDatabase.getReference("TripsAndParticipants").child(tripId)
//
//        participantsListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val aId = snapshot.child("userId").getValue(String::class.java)!!
//                    participantsIdList.add(aId)
//                }
//                viewModel.getParticipants(participantsIdList)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        }
//        participantsReference.addValueEventListener(participantsListener)
    }

    private fun readMessages(tripId: String) {
//        val reference = firebaseDatabase.getReference("Messages").child(tripId)
//        messageQuery = reference.limitToLast(1000)
//        Log.i("READ_MESSAGES", "readMessages")
//        messageChildEventListener = object : ChildEventListener {
//
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)
//                dataSnapshot.getValue(ChatMessage::class.java)?.let {
//                    viewModel.loadMessages.value = true
//                    viewModel.loadChatMessage(it)
//                }
//
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        }
//        messageQuery.addChildEventListener(messageChildEventListener)


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


    //onClickListenerForAdapter
    private fun onPassengerClick(aPassenger: Passenger) {
        if (viewModel.user.value?.id.toString() != aPassenger.user.id) {
            activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to aPassenger.user.id))
        } else {
            findNavController()?.navigate(R.id.action_chatFragment_to_destination_info)
        }


    }

    private fun exitCustomDialogAnswerType(result: AnswerType) {
//        tripInvolved?.let {
//            if (result == AnswerType.Yes) {
//                flag = false
//                //if it Active delete or exit whether the user is the creator or not
//                //Delete trip use case on view model
//                if (it.creatorUser.id == viewModel.user.value?.id.toString()) {
//                    if (it.status == TripStatus.ACTIVE) {
//                        viewModel.deleteTrip(it.id, participantsIdList)
//                        binding.overlappingPanels.openStartPanel()
//
//                    } else {
//                        createToast(getString(R.string.can_not_delete_trip))
//                    }
//                } else {
//                    if (it.status == TripStatus.ACTIVE) {
//                        viewModel.exitFromTrip(it.id, participantsIdList)
//                        onBackPressed()
//                    } else {
//                        createToast(getString(R.string.can_not_leave_trip))
//                    }
//                }
////                if (it.status == TripStatus.ACTIVE) {
////                    onBackPressed()
////                }
//
//            }
//        }

    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            messageQuery.removeEventListener(messageChildEventListener)
            participantsReference.removeEventListener(participantsListener)
        } catch (e: java.lang.Exception) {

        }
//        if (flag) {
//            viewModel.updateSeenStatus(args.conversationItem.tripId)
//        }
    }


}
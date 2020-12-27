package gr.fellow.fellow_traveller.ui.home.chat

import androidx.annotation.NonNull
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChatBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.MessagesAdapter
import gr.fellow.fellow_traveller.ui.home.chat.models.Message
import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private val messagesList = mutableListOf<Message>()
    private val participantsIdList: ArrayList<String> = ArrayList()

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase


    override fun getViewBinding(): FragmentChatBinding = FragmentChatBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        getAllParticipantsId(args.conversationItem.tripId)
    }

    override fun setUpViews() {
        binding.chatHeader.text = args.conversationItem.tripName

        binding.writeEtChat.text
        messagesList.clear()
        messagesList.add(Message("uyugfafkdgskjgk234kfdkf", "Καλημέραααα", "fsj'dghsdfgsgjlg.zsd", 1608218126, "John", "default"))
        messagesList.add(Message("uyugfafkdgskjgk234kfdkf", "Καλημέραααα", "fsj'dghsdfgsgjlg.zsd", 1608218126, "John", "default"))
        messagesList.add(Message("uyugfafkdgskjgk234kfdkf", "Καλημέραααα", "fsj'dghsdfgsgjlg.zsd", 1608218126, "John", "default"))
        messagesList.add(Message("uyugfafkdgskjgk234kfdkf", "Καλημέραααα", "fsj'dghsdfgsgjlg.zsd", 1608218126, "John", "default"))

        //Parse elements in adapter and on click listener
        binding.chatRecyclerView.adapter = MessagesAdapter(messagesList)

        binding.chatRecyclerView.adapter?.notifyDataSetChanged()

        binding.chatSendButton.setOnClickListener {
            //MessageType: When 0: message, When 1: event
            viewModel.sendFirebaseMessage(binding.writeEtChat.text.toString(), args.conversationItem.tripId, 0, participantsIdList)
        }

    }

    private fun getAllParticipantsId(tripId: String) {
        participantsIdList.clear()
        val reference = firebaseDatabase.getReference("TripsAndParticipants").child(tripId)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val aId = snapshot.child("userId").getValue(String::class.java)!!
                    participantsIdList.add(aId)
                }
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {}
        })
    }


}
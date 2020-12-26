package gr.fellow.fellow_traveller.ui.home.tabs
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentConversationsBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.ConversationsAdapter
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation

@AndroidEntryPoint
class ConversationsFragment : BaseFragment<FragmentConversationsBinding>() {
    private val conversationsList = mutableListOf<Conversation>()
    private val onItemClickListener: (Conversation) -> Unit = this::onConversationClick

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentConversationsBinding =
        FragmentConversationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {


    }

    override fun setUpViews() {

        conversationsList.clear()
//        conversationsList.add(Conversation("1", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το παρωί αν είναι", 1608218840, "default", false))
//        conversationsList.add(Conversation("2", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("3", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("4", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("5", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("6", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("7", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
//        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))

        loadConversations(viewModel.user.value?.id.toString())
        //Parse elements in adapter and on click listener
        //binding.recyclerView.adapter = ConversationsAdapter(conversationsList, this::onConversationClick)

        //binding.recyclerView.adapter?.notifyDataSetChanged()

    }

    //onClickListenerForAdapter
    private fun onConversationClick(aConversation: Conversation) {
        createToast(aConversation.tripId)
        //findNavController()?.navigate(R.id.action_destination_messenger_to_chatFragment)
        findNavController()?.navigate(
            R.id.action_destination_messenger_to_chatFragment, bundleOf("conversationItem" to aConversation)
        )

    }

    private fun loadConversations(userId: String) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("UserTrips").child(userId)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                conversationsList.clear()
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val conversation = dataSnapshot.getValue(Conversation::class.java)

                    if (conversation != null) {
                        conversationsList.add(conversation)
                    }
                }

                binding.recyclerView.adapter = ConversationsAdapter(conversationsList, onItemClickListener)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


}


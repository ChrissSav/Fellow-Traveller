package gr.fellow.fellow_traveller.ui.home.tabs

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentConversationsBinding
import gr.fellow.fellow_traveller.service.NotificationJobService.Companion.TAG
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.ConversationsAdapter
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation
import javax.inject.Inject

@AndroidEntryPoint
class ConversationsFragment : BaseFragment<FragmentConversationsBinding>() {
    private val conversationsList = mutableListOf<Conversation>()

    //private val onItemClickListener: (Conversation) -> Unit = this::onConversationClick
    private val viewModel: HomeViewModel by activityViewModels()

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    override fun getViewBinding(): FragmentConversationsBinding =
        FragmentConversationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.conversationList.observe(viewLifecycleOwner, {

            conversationsList.clear()
            conversationsList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })

    }

    override fun setUpViews() {

        //conversationsList.clear()


        binding.recyclerView.adapter = ConversationsAdapter(conversationsList, this::onConversationClick)
        loadConversations(viewModel.user.value?.id.toString())


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
        val reference: DatabaseReference = firebaseDatabase.getReference("UserTrips").child(userId)

        val conversationListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.childrenCount > 0) {

                    val tempList = mutableListOf<Conversation>()

                    dataSnapshot.children.forEach {
                        it.getValue(Conversation::class.java)?.let { conversation ->
                            tempList.add(conversation)
                        }
                    }

                    viewModel.loadConversations(tempList)


                }

//                if (conversationsList.isNullOrEmpty())
//                    binding.messagesSection.visibility = View.VISIBLE
//                else
//                    binding.messagesSection.visibility = View.GONE

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadConversation:onCancelled", databaseError.toException())
                // ...
            }
        }
        reference.addValueEventListener(conversationListener)


//        val childEventListener = object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)
//
//                // Get Post object and use the values to update the UI
//                val conversation = dataSnapshot.getValue(Conversation::class.java)
//
//                if (conversation != null) {
//                    conversationsList.add(conversation)
//                }
//                    binding.recyclerView.adapter?.notifyDataSetChanged()
//
//
//                // ...
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
//
//            }
//        }
//        reference.addChildEventListener(childEventListener)

    }


}


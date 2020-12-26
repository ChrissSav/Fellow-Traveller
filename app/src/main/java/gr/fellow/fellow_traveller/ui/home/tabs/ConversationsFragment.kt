package gr.fellow.fellow_traveller.ui.home.tabs

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
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

    //private val onItemClickListener: (Conversation) -> Unit = this::onConversationClick
    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentConversationsBinding =
        FragmentConversationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.conversationList.observe(viewLifecycleOwner, { list ->

            list.sortByDescending { it.timestamp }

            (binding.recyclerView.adapter as ConversationsAdapter).submitList(list)


            if (!list.isNullOrEmpty())
                binding.messagesSection.visibility = View.GONE
        })

    }

    override fun setUpViews() {

        //conversationsList.clear()

        binding.recyclerView.adapter = ConversationsAdapter(this::onConversationClick)

        binding.messagesSection.visibility = View.VISIBLE

    }

    //onClickListenerForAdapter
    private fun onConversationClick(aConversation: Conversation) {
        createToast(aConversation.tripId)
        //findNavController()?.navigate(R.id.action_destination_messenger_to_chatFragment)
        findNavController()?.navigate(
            R.id.action_destination_messenger_to_chatFragment, bundleOf("conversationItem" to aConversation)
        )

    }


}


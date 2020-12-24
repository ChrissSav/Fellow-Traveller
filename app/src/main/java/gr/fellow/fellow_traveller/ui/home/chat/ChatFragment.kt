package gr.fellow.fellow_traveller.ui.home.chat

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChatBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.MessagesAdapter
import gr.fellow.fellow_traveller.ui.home.chat.models.Message

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private val args: ChatFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private val messagesList = mutableListOf<Message>()


    override fun getViewBinding(): FragmentChatBinding = FragmentChatBinding.inflate(layoutInflater)

    override fun setUpObservers() {

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
            viewModel.sendFirebaseMessage(binding.writeEtChat.text.toString(), "gsdkjgksgo435sf")
        }

    }


}
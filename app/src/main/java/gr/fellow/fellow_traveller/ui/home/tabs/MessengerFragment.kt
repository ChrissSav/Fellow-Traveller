package gr.fellow.fellow_traveller.ui.home.tabs

import androidx.fragment.app.activityViewModels
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentMessengerBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.ConversationsAdapter
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation


class MessengerFragment : BaseFragment<FragmentMessengerBinding>() {
    private val conversationsList = mutableListOf<Conversation>()

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentMessengerBinding =
        FragmentMessengerBinding.inflate(layoutInflater)



    override fun setUpObservers() {


    }

    override fun setUpViews() {

        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πδασδασδασδασδαδασφασφαφσαφσαφαφαασφασφαρωί αν είναι", 1608218840, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        conversationsList.add(Conversation("ufsdfsf2432", "Φιλώτας-Πτολεμαΐδα", "Ναιι θα περάσω το πρωί αν είναι", 1608218126, "default", false))
        binding.recyclerView.adapter = ConversationsAdapter(conversationsList)

        //binding.recyclerView.adapter?.notifyDataSetChanged()
        createToast(conversationsList.get(0).description)
    }





}


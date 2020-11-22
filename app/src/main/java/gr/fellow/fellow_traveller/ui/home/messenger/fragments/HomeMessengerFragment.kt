package gr.fellow.fellow_traveller.ui.home.messenger.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeMessengerBinding
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel

@AndroidEntryPoint
class HomeMessengerFragment : BaseFragment<FragmentHomeMessengerBinding>() {

    val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentHomeMessengerBinding =
        FragmentHomeMessengerBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.messengerLink.setText(it.messengerLink)
            binding.messengerLink.clearFocus()
        })

    }

    override fun setUpViews() {

        binding.save.setOnClickListener {
            if (binding.messengerLink.text.isNullOrEmpty()) {
                viewModel.setErrorMessage(R.string.check_field)
            } else {
                viewModel.changeMessenger(binding.messengerLink.text.toString())
            }
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

}
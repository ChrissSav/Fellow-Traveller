package gr.fellow.fellow_traveller.ui.home.messenger.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeMessengerBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.messenger.MessengerLinkActivity

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

        viewModel.messengerUpdate.observe(viewLifecycleOwner, Observer {
            createToast(getString(R.string.messenger_profile_saved_successfully))
            onBackPressed()
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
        binding.help.setOnClickListener {
            hideKeyboard()
            startActivityForResultWithFade(MessengerLinkActivity::class, 10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                val messenger = data?.getStringExtra("messenger")
                messenger?.let {
                    viewModel.changeMessenger(it)
                }
            }
        }
    }

}
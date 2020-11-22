package gr.fellow.fellow_traveller.ui.home.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBaseSettingsBinding
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.messenger.MessengerLinkActivity

@AndroidEntryPoint
class BaseSettingsFragment : BaseFragment<FragmentBaseSettingsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentBaseSettingsBinding =
        FragmentBaseSettingsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            with(binding) {
                userEmailAddressTextView.text = it.email
                userFullNameTextView.text = "${it.firstName} ${it.lastName}"
                profilePictureSettings.loadImageFromUrl(it.picture)
            }
        })

    }

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.writeReview.setOnClickListener {
            activity?.startActivityWithFade(MessengerLinkActivity::class, null)
        }

        binding.personalInfo.setOnClickListener {
            findNavController()?.navigateWithAnimation(R.id.action_baseSettingsFragment_to_accountSettingsFragment)

        }

        binding.password.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_changePasswordFragment)
        }

        binding.myCars.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_userCarsFragment)
        }

        binding.messenger.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_homeMessengerFragment)
        }

        binding.logout.setOnClickListener {
            viewModel.logOut()
        }
    }


}
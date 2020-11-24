package gr.fellow.fellow_traveller.ui.home.settings

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBaseSettingsBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.navigateWithAnimation
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel

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
        binding.termsOfUse.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://fellowtraveller.gr/tos/"))
            startActivity(browserIntent)
        }
        binding.privacyPolicy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://fellowtraveller.gr/tos/"))
            startActivity(browserIntent)
        }
        binding.logout.setOnClickListener {
            viewModel.logOut()
        }
    }


}
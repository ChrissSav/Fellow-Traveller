package gr.fellow.fellow_traveller.ui.home.settings

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBaseSettingsBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel

@AndroidEntryPoint
class BaseSettingsFragment : BaseFragment<FragmentBaseSettingsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentBaseSettingsBinding =
        FragmentBaseSettingsBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }



        binding.personalInfo.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_accountSettingsFragment)

        }

        binding.password.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_changePasswordFragment)
        }


        binding.messenger.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_homeMessengerFragment)
        }
        binding.termsOfUse.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.TOS_URL)))
            startActivity(browserIntent)
        }
        binding.privacyPolicy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.PRIVACY_POLICY_URL)))
            startActivity(browserIntent)
        }
        binding.logout.setOnClickListener {
            viewModel.logOut()
        }
    }


}
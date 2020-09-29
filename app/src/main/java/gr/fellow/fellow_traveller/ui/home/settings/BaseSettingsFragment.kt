package gr.fellow.fellow_traveller.ui.home.settings

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBaseSettingsBinding
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.ui.onBackPressed

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

        viewModel.logout.observe(viewLifecycleOwner, Observer {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })
    }

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.personalUserInfoButton.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_accountSettingsFragment)

        }

        binding.manageUserCarsButton.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_userCarsFragment)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logOut()
        }
    }


}
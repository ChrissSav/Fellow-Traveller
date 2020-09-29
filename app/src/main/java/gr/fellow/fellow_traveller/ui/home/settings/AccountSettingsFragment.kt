package gr.fellow.fellow_traveller.ui.home.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentAccountSettingsBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.onBackPressed


@AndroidEntryPoint
class AccountSettingsFragment : BaseFragment<FragmentAccountSettingsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentAccountSettingsBinding =
        FragmentAccountSettingsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            with(binding) {
                PersonalSettingsActivityEditTextFirstName.setText(it.firstName)
                PersonalSettingsActivityEditTextLastName.setText(it.lastName)
                PersonalSettingsActivityEditTextAboutMe.setText(it.aboutMe)
                PersonalSettingsActivityEditTextPhone.setText(it.phone)
                userImage.loadImageFromUrl(it.picture)

                PersonalSettingsActivityButtonClose.setOnClickListener {
                    onBackPressed()
                }
            }
        })
    }

    override fun setUpViews() {

    }


}
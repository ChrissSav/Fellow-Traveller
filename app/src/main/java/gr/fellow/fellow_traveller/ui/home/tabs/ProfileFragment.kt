package gr.fellow.fellow_traveller.ui.home.tabs

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentProfileBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var messengerLink: String? = null


    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->

            with(binding) {
                userName.text = "${user.firstName} ${user.lastName}"
                userImage.loadImageFromUrl(user.picture)
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                searches.text = "20"
                offers.text = "13"
                this@ProfileFragment.messengerLink = user.messengerLink
                if (!user.aboutMe.isNullOrEmpty())
                    aboutMe.setText(user.aboutMe)
            }
        })
    }

    override fun setUpViews() {
        binding.settingsButton.setOnClickListener {
            findNavController()?.navigate(R.id.to_setting)
        }

        binding.messengerLink.setOnClickListener {
            messengerLink?.let {
                val uriUrl: Uri = Uri.parse(getString(R.string.messenger_link, it))
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }

        }
    }

}
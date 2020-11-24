package gr.fellow.fellow_traveller.ui.home.tabs

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentProfileBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var messengerLink: String? = null


    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.reviews.observe(this, Observer { list ->
            list?.let {
                if (it.isNotEmpty()) {


                    val first = it.first()

                    binding.reviewItem.picture.loadImageFromUrl(first.user.picture)
                    binding.reviewItem.date.text = getDateFromTimestamp(first.timestamp, "d MMM yyyy")
                    binding.reviewItem.rate.text = first.rate.toString()
                    binding.reviewItem.username.text = first.user.fullName.toString()
                    //binding.viewAll.visibility = View.VISIBLE
                    //binding.reviewsConstraintLayout.visibility = View.VISIBLE
                }
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->

            with(binding) {
                userName.text = "${user.firstName} ${user.lastName}"
                userImage.loadImageFromUrl(user.picture)
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                involved.text = user.tripsInvolved.toString()
                offers.text = user.tripsOffers.toString()
                this@ProfileFragment.messengerLink = user.messengerLink
                if (!user.aboutMe.isNullOrEmpty())
                    aboutMe.setText(user.aboutMe)
            }
        })
    }

    override fun setUpViews() {

        viewModel.loadUserInfo()
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
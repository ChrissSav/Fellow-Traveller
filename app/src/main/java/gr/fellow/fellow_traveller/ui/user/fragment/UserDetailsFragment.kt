package gr.fellow.fellow_traveller.ui.user.fragment

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserDetailsBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.user.UserInfoDetailsViewModel
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    private val viewModel: UserInfoDetailsViewModel by activityViewModels()
    private var messengerLink: String? = null


    override fun getViewBinding(): FragmentUserDetailsBinding =
        FragmentUserDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.user.observe(this, Observer { user ->
            with(binding) {
                if (user.messengerLink.isNullOrEmpty())
                    messengerLink.visibility = View.INVISIBLE
                userImage.loadImageFromUrl(user.picture)
                userName.text = user.fullName
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                involved.text = user.tripsInvolved.toString()
                offers.text = user.tripsOffers.toString()
                this@UserDetailsFragment.messengerLink = user.messengerLink
                if (!user.aboutMe.isNullOrEmpty())
                    aboutMe.text = user.aboutMe
                constraintLayout.visibility = View.VISIBLE
                binding.error.visibility = View.INVISIBLE
            }

        })

        viewModel.reviews.observe(this, Observer { list ->

            if (list.isNotEmpty()) {


                val first = list.first()

                binding.rateItem.picture.loadImageFromUrl(first.user.picture)
                binding.rateItem.date.text = getDateFromTimestamp(first.timestamp, "d MMM yyyy")
                binding.rateItem.rate.text = first.rate.toString()
                binding.rateItem.username.text = first.user.fullName

                binding.viewAll.visibility = View.VISIBLE
                binding.reviewsConstraintLayout.visibility = View.VISIBLE
            }

        })

        viewModel.error.observe(this, Observer {
            binding.error.visibility = View.VISIBLE
        })

    }

    override fun setUpViews() {


        binding.backButton.setOnClickListener {
            activity?.finish()
        }

        binding.messengerLink.setOnClickListener {
            messengerLink?.let {
                val uriUrl: Uri = Uri.parse(getString(R.string.messenger_link, it))
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }

        }
        binding.messengerLink1.setOnClickListener {
            messengerLink?.let {
                val uriUrl: Uri = Uri.parse(getString(R.string.messenger_link, it))
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }

        }

        binding.viewAll.setOnClickListener {
            findNavController()?.navigate(R.id.action_userDetailsFragment_to_userReviewsFragment)
        }
    }

}
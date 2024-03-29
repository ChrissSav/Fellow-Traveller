package gr.fellow.fellow_traveller.ui.user.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentUserDetailsBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.startShimmerWithVisibility
import gr.fellow.fellow_traveller.ui.extensions.stopShimmerWithVisibility
import gr.fellow.fellow_traveller.ui.user.UserInfoDetailsViewModel
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    private val viewModel: UserInfoDetailsViewModel by activityViewModels()


    override fun getViewBinding(): FragmentUserDetailsBinding =
        FragmentUserDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            with(binding) {
                userImage.loadImageFromUrl(user.picture)
                userName.text = user.fullName
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                involved.text = user.tripsInvolved.toString()
                offers.text = user.tripsOffers.toString()
                if (!user.aboutMe.isNullOrEmpty())
                    aboutMe.setText(user.aboutMe)
                constraintLayout.visibility = View.VISIBLE


                binding.userImage.setOnClickListener {
                    openDialogPicture(user.picture)
                }

                binding.shimmerViewContainer.stopShimmerWithVisibility()

            }

        })

        viewModel.load.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.shimmerViewContainer.startShimmerWithVisibility()
            else
                binding.shimmerViewContainer.stopShimmerWithVisibility()

        })

        viewModel.reviews.observe(viewLifecycleOwner, Observer { list ->

            if (list.isNotEmpty()) {
                val first = list.first()
                binding.rateItem.picture.loadImageFromUrl(first.user.picture)
                binding.rateItem.date.text = getDateFromTimestamp(first.timestamp, "d MMM yyyy")
                binding.rateItem.rate.text = first.rate.toString()
                binding.rateItem.username.text = first.user.fullName

                binding.viewAll.visibility = View.VISIBLE
                binding.reviewsConstraintLayout.visibility = View.VISIBLE
                binding.noReview.visibility = View.GONE

            } else {
                binding.noReview.visibility = View.VISIBLE
            }

        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            binding.shimmerViewContainer.startShimmerWithVisibility()
        })

    }

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            activity?.finish()
        }

        binding.viewAll.setOnClickListener {
            findNavController()?.navigate(R.id.action_userDetailsFragment_to_userReviewsFragment)
        }
    }

}

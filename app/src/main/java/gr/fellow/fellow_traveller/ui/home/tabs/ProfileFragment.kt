package gr.fellow.fellow_traveller.ui.home.tabs

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
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

            if (list.isNotEmpty()) {


                val first = list.first()

                binding.reviewItem.picture.loadImageFromUrl(first.user.picture)
                binding.reviewItem.date.text = getDateFromTimestamp(first.timestamp, "d MMM yyyy")
                binding.reviewItem.rate.text = first.rate.toString()
                binding.reviewItem.username.text = first.user.fullName
                //binding.viewAll.visibility = View.VISIBLE
                //binding.reviewsConstraintLayout.visibility = View.VISIBLE
            } else {
                //GONE the constraints
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
                    aboutMe.text = user.aboutMe
            }
        })
        viewModel.cars.observe(viewLifecycleOwner, Observer { car ->

            with(binding) {

                try {
                    val firstCar = car.first()
                    carItem.brand.text = firstCar.brand
                    carItem.model.text = firstCar.model
                    carItem.plate.text = firstCar.plate
                    carItem.color.text = firstCar.color
                    //binding.viewAll.visibility = View.INVISIBLE
                    //binding.reviewsConstraintLayout.visibility = View.INVISIBLE
                } catch (e: NoSuchElementException) {
                    //binding.viewAll.visibility = View.GONE
                    //binding.reviewsConstraintLayout.visibility = View.GONE
                }

            }
        })
    }

    override fun setUpViews() {

        binding.allReviews.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_profileReviewsFragment)
        }
        binding.settingsButton.setOnClickListener {
            findNavController()?.navigate(R.id.to_setting)
        }
        binding.allCars.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_userCarsFragment)
        }
        binding.allSearchs.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_tripInvolvedHistoryFragment, bundleOf("creator" to false))
        }
        binding.allOffers.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_tripInvolvedHistoryFragment, bundleOf("creator" to true))
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
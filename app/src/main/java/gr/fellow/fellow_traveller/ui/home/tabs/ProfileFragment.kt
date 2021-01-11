package gr.fellow.fellow_traveller.ui.home.tabs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.discord.panels.OverlappingPanelsLayout
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentProfileBinding
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResult
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.reviews.observe(this, { list ->

            if (list.isNotEmpty()) {


                val first = list.first()

                binding.reviewItem.picture.loadImageFromUrl(first.user.picture)
                binding.reviewItem.date.text = getDateFromTimestamp(first.timestamp, "d MMM yyyy")
                binding.reviewItem.rate.text = first.rate.toString()
                binding.reviewItem.username.text = first.user.fullName

                binding.reviewSection.visibility = VISIBLE
                //binding.reviewsConstraintLayout.visibility = View.VISIBLE
            } else {
                binding.reviewSection.visibility = GONE
            }

        })

        viewModel.user.observe(viewLifecycleOwner, { user ->

            with(binding) {


                userName.text = "${user.firstName} ${user.lastName}"
                userImage.loadImageFromUrl(user.picture)
                reviews.text = user.reviews.toString()
                rate.text = user.rate.toString()
                involved.text = user.tripsInvolved.toString()
                offers.text = user.tripsOffers.toString()
                if (!user.aboutMe.isNullOrEmpty())
                    aboutMe.text = user.aboutMe

                binding.userImage.setOnClickListener {
                    openDialogPicture(user.picture)
                }
            }
        })
        viewModel.cars.observe(viewLifecycleOwner, { car ->

            with(binding) {

                try {
                    val firstCar = car.first()
                    carItem.brand.text = firstCar.brand
                    carItem.model.text = firstCar.model
                    carItem.plate.text = firstCar.plate
                    carItem.color.text = firstCar.color

                    binding.carSection.visibility = VISIBLE
                    binding.addCarSection.visibility = GONE

                } catch (e: NoSuchElementException) {
                    //binding.viewAll.visibility = View.GONE
                    binding.carSection.visibility = GONE
                    binding.addCarSection.visibility = VISIBLE
                }

            }
        })
    }

    override fun setUpViews() {

        binding.overlappingPanels.setStartPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)

        binding.allReviews.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_profileReviewsFragment)
        }
        binding.settingsButton.setOnClickListener {
            binding.overlappingPanels.openEndPanel()
        }
        binding.allCars.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_userCarsFragment)
        }
        binding.allBookings.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_tripInvolvedHistoryFragment, bundleOf("creator" to false))
        }
        binding.allOffers.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_tripInvolvedHistoryFragment, bundleOf("creator" to true))
        }
        binding.addCarSection.setOnClickListener {
            startActivityForResult(AddCarActivity::class, 1, null)
        }


        //Second Frame

        binding.settings.personalInfo.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_accountSettingsFragment)

        }

        binding.settings.password.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_changePasswordFragment)
        }

        binding.settings.writeReview.setOnClickListener {
            val uri = Uri.parse(getString(R.string.PLAYSTORE_RATE) + activity?.applicationContext?.packageName)
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(browserIntent)
            } catch (e: Exception) {
                createToast(e.toString())
            }

        }
        binding.settings.termsOfUse.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.TOS_URL)))
            startActivity(browserIntent)
        }

        binding.settings.privacyPolicy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.PRIVACY_POLICY_URL)))
            startActivity(browserIntent)
        }

        binding.settings.logout.setOnClickListener {
            viewModel.logOut()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadCars(true)
            }
        }
    }

}
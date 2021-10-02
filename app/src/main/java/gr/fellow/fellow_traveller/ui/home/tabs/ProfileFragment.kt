package gr.fellow.fellow_traveller.ui.home.tabs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
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


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)


    override fun setUpObservers() {



        viewModel.user.observe(viewLifecycleOwner, { user ->

            with(binding) {


                username.text = "${user.firstName} ${user.lastName}"
                picture.loadImageFromUrl(user.picture)
                reviews.text = user.reviews.toString()
                involved.text = user.tripsInvolved.toString()
                offers.text = user.tripsOffers.toString()
//                if (!user.aboutMe.isNullOrEmpty())
//                    aboutMe.text = user.aboutMe

                binding.picture.setOnClickListener {
                    openDialogPicture(user.picture)
                }
            }
        })
//        viewModel.cars.observe(viewLifecycleOwner, { car ->
//
//            with(binding) {
//
//                try {
//                    val firstCar = car.first()
//                    carItem.brand.text = firstCar.brand
//                    carItem.model.text = firstCar.model
//                    carItem.plate.text = firstCar.plate
//                    carItem.color.text = firstCar.color
//
//                    binding.carSection.visibility = VISIBLE
//                    binding.addCarSection.visibility = GONE
//
//                } catch (e: NoSuchElementException) {
//                    //binding.viewAll.visibility = View.GONE
//                    binding.carSection.visibility = GONE
//                    binding.addCarSection.visibility = VISIBLE
//                }
//
//            }
//        })
    }

    override fun setUpViews() {


        binding.allReviews.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_info_to_profileReviewsFragment)
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

        binding.personalInfo.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_accountSettingsFragment)

        }

        binding.password.setOnClickListener {
            findNavController()?.navigate(R.id.action_baseSettingsFragment_to_changePasswordFragment)
        }

        binding.writeReview.setOnClickListener {
            val uri = Uri.parse(getString(R.string.PLAYSTORE_RATE) + activity?.applicationContext?.packageName)
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(browserIntent)
            } catch (e: Exception) {
                createToast(e.toString())
            }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadCars(true)
            }
        }
    }

}
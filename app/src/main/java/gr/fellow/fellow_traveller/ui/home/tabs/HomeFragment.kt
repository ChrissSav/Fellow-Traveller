package gr.fellow.fellow_traveller.ui.home.tabs

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.BuildConfig
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.openUrl
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResult
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripActivity

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()
    private var accountCorrect = false

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            accountCorrect = it.messengerLink != null


            //Delete the "ς" or "s" when we display the name
            if (it.firstName.takeLast(1).equals("ς"))
                binding.userName.text = it.firstName.dropLast(1) + ","
            else
                binding.userName.text = it.firstName + ","

        })
    }

    override fun setUpViews() {

        binding.constraintLayoutNew.setOnClickListener {
            if (accountCorrect) {
                startActivityForResult(NewTripActivity::class, 1, null)
            } else {
                createAlerter(getString(R.string.complete_profile_warning))
            }
        }

        binding.newTrip.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(NewTripActivity::class, 1, bundleOf("userRate" to viewModel.user.value?.rate))
            else
                createAlerter(getString(R.string.complete_profile_warning))
        }

        binding.constraintLayoutSearch.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(SearchTripActivity::class, 2, null)
            else
                createAlerter(getString(R.string.complete_profile_warning))
        }

        binding.searchTrip.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(SearchTripActivity::class, 2, null)
            else
                createAlerter(getString(R.string.complete_profile_warning))
        }
        binding.help.setOnClickListener {
            activity?.openUrl(BuildConfig.FELLOW_WEB_SITE_URL)
        }
        binding.guide.setOnClickListener {
            activity?.openUrl(BuildConfig.FELLOW_WEB_SITE_URL)
        }
        binding.searchAndOffer.setOnClickListener {
            activity?.openUrl(BuildConfig.FELLOW_WEB_SITE_URL)
        }

        binding.cityChoice1.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FSKG%20-%20Athens.jpg?alt=media&token=6ed5258b-c268-4fc5-8236-c9af098d187e")
        binding.cityChoice2.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FIOA%20-%20ATH%202.jpg?alt=media&token=e7dca60f-cc8f-416f-9639-c9a0233b428a")
        binding.cityChoice3.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FIOA%20-%20PAT%202%20.jpg?alt=media&token=edefdd27-078c-4524-bce9-5c498c120fe1")
        binding.cityChoice4.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FKAB%20-%20SKG.jpg?alt=media&token=39ba040f-9705-445c-a2a6-c8b478406133")

        binding.cityChoice1.setOnClickListener {
            if (accountCorrect) {
                val placeFrom = PlaceModel("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.placeholder_city_thessaloniki), 40.634781.toFloat(), 22.943090.toFloat())
                val placeTo = PlaceModel("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.placeholder_city_athens), 37.97534.toFloat(), 23.736151.toFloat())
                startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo))
            } else
                createAlerter(getString(R.string.complete_profile_warning))
        }

        binding.cityChoice2.setOnClickListener {
            if (accountCorrect) {
                val placeFrom = PlaceModel("ChIJZ93-3qLpWxMRwJe54iy9AAQ", getString(R.string.placeholder_city_ioannina), 39.674530.toFloat(), 20.840210.toFloat())
                val placeTo = PlaceModel("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.placeholder_city_athens), 37.97534.toFloat(), 23.736151.toFloat())
                startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo))
            } else
                createAlerter(getString(R.string.complete_profile_warning))
        }

        binding.cityChoice3.setOnClickListener {
            if (accountCorrect) {
                val placeFrom = PlaceModel("ChIJZ93-3qLpWxMRwJe54iy9AAQ", getString(R.string.placeholder_city_ioannina), 39.674530.toFloat(), 20.840210.toFloat())
                val placeTo = PlaceModel("ChIJLe0kpZk1XhMRoIy54iy9AAQ", getString(R.string.placeholder_city_patra), 38.246639.toFloat(), 21.734573.toFloat())
                startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo))
            } else
                createAlerter(getString(R.string.complete_profile_warning))

        }

        binding.cityChoice4.setOnClickListener {
            if (accountCorrect) {
                val placeFrom = PlaceModel("ChIJAfxmkHK7rhQRbEdqRDfhZ_U", getString(R.string.placeholder_city_kavala), 40.937607.toFloat(), 24.412866.toFloat())
                val placeTo = PlaceModel("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.placeholder_city_thessaloniki), 40.634781.toFloat(), 22.943090.toFloat())
                startActivityForResult(SearchTripActivity::class, 2, bundleOf("placeFrom" to placeFrom, "placeTo" to placeTo))
            } else
                createAlerter(getString(R.string.complete_profile_warning))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                    viewModel.addTripCreate(it)
                }
            }

        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                    viewModel.addTripPassenger(it)
                }
            }

        }
    }


}
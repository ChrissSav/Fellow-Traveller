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
        binding.cityChoice1.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FSKG-ATH.jpg?alt=media&token=ea8aac0d-b2af-4f09-af83-2db87e69dcf4")
        binding.cityChoice2.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FIOA-ATH.jpg?alt=media&token=e374487c-9715-49ea-b972-25384cb90e42")
        binding.cityChoice3.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FIOA-PAT.jpg?alt=media&token=0fb958e3-9027-4a10-9582-7262f7555a5c")
        binding.cityChoice4.loadImageFromUrl("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2FKAV-SKG.jpg?alt=media&token=e08813a7-53a7-424e-9077-12d42e24f086")
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
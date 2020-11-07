package gr.fellow.fellow_traveller.ui.home.tabs

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
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
            if (accountCorrect)
                startActivityForResult(NewTripActivity::class, 1)
            else
                createAlerter("Πρέπει να ολοκληρώσετε το προφίλ σας πρώτα !")
        }

        binding.newTrip.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(NewTripActivity::class, 1)
            else
                createAlerter("Πρέπει να ολοκληρώσετε το προφίλ σας πρώτα !")
        }

        binding.constraintLayoutSearch.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(SearchTripActivity::class, 2)
            else
                createAlerter("Πρέπει να ολοκληρώσετε το προφίλ σας πρώτα !")

        }

        binding.searchTrip.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(SearchTripActivity::class, 2)
            else
                createAlerter("Πρέπει να ολοκληρώσετε το προφίλ σας πρώτα !")

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
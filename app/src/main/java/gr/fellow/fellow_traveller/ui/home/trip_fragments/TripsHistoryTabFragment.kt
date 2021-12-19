package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripsOffersBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsActiveAdapter
import gr.fellow.fellow_traveller.ui.home.trip_details.TripDetailsActivity


@AndroidEntryPoint
class TripsHistoryTabFragment : BaseFragment<FragmentTripsOffersBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentTripsOffersBinding =
        FragmentTripsOffersBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.tripsHistory.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerView.adapter as TripsActiveAdapter).submitList(null)
            (binding.recyclerView.adapter as TripsActiveAdapter).submitList(list)
            binding.swipeRefreshLayout.isRefreshing = false

            //Check if we have active trips
            if (list.isNullOrEmpty())
                binding.tripsNotFound.visibility = View.VISIBLE
            else
                binding.tripsNotFound.visibility = View.GONE
        })

    }

    override fun setUpViews() {
        viewModel.loadHistoryTrips()
        binding.recyclerView.adapter = TripsActiveAdapter(viewModel.currentUser.id, this@TripsHistoryTabFragment::onTripClick)


        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as TripsActiveAdapter).submitList(null)
            viewModel.loadHistoryTrips(true)
        }
    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        startActivityForResultWithFade(TripDetailsActivity::class, 2, bundleOf("trip" to tripInvolved))
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                    // viewModel.addTripCreate(it)
                }
            }

        }
    }


}


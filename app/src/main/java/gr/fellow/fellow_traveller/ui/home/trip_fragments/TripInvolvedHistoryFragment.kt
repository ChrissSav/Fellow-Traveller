package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedHistoryBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startShimmerWithVisibility
import gr.fellow.fellow_traveller.ui.extensions.stopShimmerWithVisibility
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsHistoryAdapter


@AndroidEntryPoint
class TripInvolvedHistoryFragment : BaseFragment<FragmentTripInvolvedHistoryBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val args: TripInvolvedHistoryFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentTripInvolvedHistoryBinding =
        FragmentTripInvolvedHistoryBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        if (args.creator) {
            viewModel.tripsAsCreatorHistory.observe(viewLifecycleOwner, Observer { list ->
                binding.swipeRefreshLayout.isRefreshing = false
                (binding.recyclerView.adapter as TripsHistoryAdapter).submitList(list)

                //Check if we have trips in history
                if (list.isNullOrEmpty())
                    binding.historySection.visibility = View.VISIBLE
                else
                    binding.historySection.visibility = View.GONE

            })
        } else {

            viewModel.tripsAsPassengerHistory.observe(viewLifecycleOwner, Observer { list ->
                binding.swipeRefreshLayout.isRefreshing = false
                (binding.recyclerView.adapter as TripsHistoryAdapter).submitList(list)

                //Check if we have trips in history
                if (list.isNullOrEmpty()) {
                    binding.historySection.visibility = View.VISIBLE
                } else {
                    binding.historySection.visibility = View.GONE
                }

            })
        }

        viewModel.loadHistory.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.historySection.visibility = View.GONE
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else
                binding.shimmerViewContainer.stopShimmerWithVisibility()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = false
        })


    }

    override fun setUpViews() {
//        if (args.creator) {
//            viewModel.loadTripsAsCreatorHistory()
//            binding.recyclerView.adapter = TripsHistoryAdapter(R.drawable.background_stroke_radius_27_green, this@TripInvolvedHistoryFragment::onTripClick)
//            binding.label.text = getString(R.string.my_offers)
//        } else {
//            viewModel.loadTripsAsPassengerHistory()
//            binding.recyclerView.adapter = TripsHistoryAdapter(R.drawable.background_stroke_radius_27_orange, this@TripInvolvedHistoryFragment::onTripClick)
//            binding.label.text = getString(R.string.my_bookings)
//        }
//
//
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            if (args.creator)
//                viewModel.loadTripsAsCreatorHistory(true)
//            else
//                viewModel.loadTripsAsPassengerHistory(true)
//
//            (binding.recyclerView.adapter as TripsHistoryAdapter).submitList(null)
//        }
//
//        binding.backButton.setOnClickListener {
//            onBackPressed()
//        }

    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(
            R.id.action_tripInvolvedHistoryFragment_to_tripInvolvedDetailsFragment,
            bundleOf("tripId" to tripInvolved.id, "reload" to false, "history" to true, "creator" to args.creator)
        )
    }

}
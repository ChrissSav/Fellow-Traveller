package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTakesPartTabBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsInvolvedHorizontalAdapter


@AndroidEntryPoint
class TripsTakePartTabFragment : BaseFragment<FragmentTakesPartTabBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentTakesPartTabBinding =
        FragmentTakesPartTabBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.tripsAsPassengerActive.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerView.adapter as TripsInvolvedHorizontalAdapter).submitList(list)
        })

        viewModel.loadPassengerActive.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.genericLoader.progressLoad.visibility = View.GONE
            }
        })


        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as TripsInvolvedHorizontalAdapter).submitList(null)
            viewModel.loadTripsAsPassenger(true)
        }

    }

    override fun setUpViews() {
        viewModel.loadTripsAsPassenger()
        binding.recyclerView.adapter = TripsInvolvedHorizontalAdapter(
            R.drawable.background_stroke_radius_27_orange,
            this@TripsTakePartTabFragment::onTripClick
        )



        binding.buttonHistory.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedHistoryFragment, bundleOf("creator" to false))
        }

    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedDetailsFragment, bundleOf("tripId" to tripInvolved.id))
    }


}
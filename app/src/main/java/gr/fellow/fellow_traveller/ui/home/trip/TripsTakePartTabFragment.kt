package gr.fellow.fellow_traveller.ui.home.trip

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
import gr.fellow.fellow_traveller.ui.home.adapter.TripsAsPassengerAdapter


@AndroidEntryPoint
class TripsTakePartTabFragment : BaseFragment<FragmentTakesPartTabBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentTakesPartTabBinding =
        FragmentTakesPartTabBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.tripsAsPassengerActive.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerView.adapter as TripsAsPassengerAdapter).submitList(list)
        })

        viewModel.loadPassengerActive.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.GONE
        })

    }

    override fun setUpViews() {
        viewModel.loadTripsAsPassenger()
        binding.recyclerView.adapter = TripsAsPassengerAdapter(onTripClickListener = this@TripsTakePartTabFragment::onTripClick)


        binding.nested.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!binding.nested.canScrollVertically(0) && binding.genericLoader.progressLoad.visibility == View.GONE)
                viewModel.loadTripsAsPassenger(true)
        }

    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedDetailsFragment, bundleOf("trip" to tripInvolved, "creator" to false))
    }

    fun resetTrips() {
        (binding.recyclerView.adapter as TripsAsPassengerAdapter).submitList(null)
        viewModel.loadTripsAsPassengerClear()
    }


}
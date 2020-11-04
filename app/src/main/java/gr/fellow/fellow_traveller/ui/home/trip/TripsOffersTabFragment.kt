package gr.fellow.fellow_traveller.ui.home.trip

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripsOffersBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsAsPassengerAdapter


@AndroidEntryPoint
class TripsOffersTabFragment : BaseFragment<FragmentTripsOffersBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var tripsList = mutableListOf<TripInvolved>()


    override fun getViewBinding(): FragmentTripsOffersBinding =
        FragmentTripsOffersBinding.inflate(layoutInflater)

    override fun setUpObservers() {


        viewModel.tripsAsCreator.observe(viewLifecycleOwner, Observer { list ->
            binding.progressBar.visibility = View.GONE
            tripsList.clear()
            tripsList.addAll(list)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        })


    }

    override fun setUpViews() {
        viewModel.loadTripsAsCreator()
        binding.recyclerView.adapter = TripsAsPassengerAdapter(R.color.LightAqua, tripsList, this@TripsOffersTabFragment::onTripClick)
    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedDetailsFragment, bundleOf("trip" to tripInvolved, "creator" to true))
    }


}


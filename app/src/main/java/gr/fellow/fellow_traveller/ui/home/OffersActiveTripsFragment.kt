package gr.fellow.fellow_traveller.ui.home

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentOffersActiveTripsBinding
import gr.fellow.fellow_traveller.domain.TripType
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.postDelay
import gr.fellow.fellow_traveller.ui.home.adapter.ActiveTripsAdapter


@AndroidEntryPoint
class OffersActiveTripsFragment : BaseFragment<FragmentOffersActiveTripsBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()
    private val tripsList = mutableListOf<TripInvolved>()
    private var type = TripType.TakesPart.name

    override fun getViewBinding(): FragmentOffersActiveTripsBinding =
        FragmentOffersActiveTripsBinding.inflate(layoutInflater)

    override fun handleIntent() {
        type = requireArguments().getString("type").toString()
    }

    override fun setUpObservers() {
        postDelay(250) {
            if (type == TripType.Offer.name)
                viewModel.tripsAsCreator.observe(viewLifecycleOwner, Observer {
                    with(binding) {
                        tripsList.clear()
                        tripsList.addAll(it)
                        recyclerView.adapter?.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                })
            else
                viewModel.tripsTakesPart.observe(viewLifecycleOwner, Observer {
                    with(binding) {
                        tripsList.clear()
                        tripsList.addAll(it)
                        recyclerView.adapter?.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                })
        }
    }

    override fun setUpViews() {
        binding.closeButton.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerView.adapter = ActiveTripsAdapter(tripsList, this::onTripClick)
    }


    private fun onTripClick(trip: TripInvolved) {

    }


}
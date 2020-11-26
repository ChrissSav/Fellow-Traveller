package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTakesPartTabBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResult
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsInvolvedAdapter
import gr.fellow.fellow_traveller.ui.search.SearchTripActivity


@AndroidEntryPoint
class TripsTakePartTabFragment : BaseFragment<FragmentTakesPartTabBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var accountCorrect = false

    override fun getViewBinding(): FragmentTakesPartTabBinding =
        FragmentTakesPartTabBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.tripsAsPassengerActive.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerView.adapter as TripsInvolvedAdapter).submitList(list)

            //Check if we have active trips
            if (list.isNullOrEmpty())
                binding.searchSection.visibility = View.VISIBLE
            else
                binding.searchSection.visibility = View.GONE
        })

        viewModel.loadPassengerActive.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.genericLoader.progressLoad.visibility = View.GONE
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            accountCorrect = it.messengerLink != null
        })

    }

    override fun setUpViews() {
        viewModel.loadTripsAsPassenger()
        binding.recyclerView.adapter = TripsInvolvedAdapter(
            R.drawable.background_stroke_radius_27_orange,
            this@TripsTakePartTabFragment::onTripClick
        )



        binding.buttonHistory.setOnClickListener {
            findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedHistoryFragment, bundleOf("creator" to false))
        }

        binding.searchButton.setOnClickListener {
            if (accountCorrect)
                startActivityForResult(SearchTripActivity::class, 2, null)
            else
                createAlerter(getString(R.string.complete_profile_warning))
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as TripsInvolvedAdapter).submitList(null)
            viewModel.loadTripsAsPassenger(true)
        }

    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedDetailsFragment, bundleOf("tripId" to tripInvolved.id))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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
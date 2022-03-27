package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripsActiveTabBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsActiveAdapter
import gr.fellow.fellow_traveller.ui.home.trip_details.TripDetailsActivity


@AndroidEntryPoint
class TripsActiveTabFragment : BaseFragment<FragmentTripsActiveTabBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentTripsActiveTabBinding =
        FragmentTripsActiveTabBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.tripsActive.observe(viewLifecycleOwner, Observer { list ->
            (binding.recyclerView.adapter as TripsActiveAdapter).submitList(list)
            binding.swipeRefreshLayout.isRefreshing = false

            //Check if we have active trips
            if (list.isNullOrEmpty()) {
                binding.tripsNotFound.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE

            } else {
                binding.tripsNotFound.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

            }
        })

        viewModel.activeTripsLoader.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.recyclerView.visibility = View.GONE
                binding.tripsNotFound.visibility = View.GONE
                binding.shimmerViewContainer.visibility = View.VISIBLE
            } else {
                binding.shimmerViewContainer.visibility = View.GONE
            }
        })
    }

    override fun setUpViews() {
        viewModel.loadActiveTrips()
        binding.recyclerView.adapter = TripsActiveAdapter(viewModel.currentUser.id, this@TripsActiveTabFragment::onTripClick)


        /*  binding.buttonHistory.setOnClickListener {
              startActivityForResult(NewTripActivity::class,
                  1,
                  bundleOf(
                      "destinationFrom" to Destination("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.placeholder_city_athens)),
                      "localUser" to viewModel.user.value!!,
                      "destinationTo" to Destination("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.placeholder_city_thessaloniki)),
                  ))

              //  findNavController()?.navigate(R.id.action_destination_trips_to_tripInvolvedHistoryFragment, bundleOf("creator" to true))
          }

          binding.offerButton.setOnClickListener {
              //startActivityForResult(NewTripActivity::class, 1, bundleOf("userRate" to viewModel.user.value?.rate, "localUser" to viewModel.user.value!!))
          }
*/

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadActiveTrips(true)
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


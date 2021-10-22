package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.app.Activity
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripsActiveTabBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class TripsActiveTabFragment : BaseFragment<FragmentTripsActiveTabBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentTripsActiveTabBinding =
        FragmentTripsActiveTabBinding.inflate(layoutInflater)

    override fun setUpObservers() {


        /*viewModel.tripsAsCreatorActive.observe(viewLifecycleOwner, Observer { list ->
            Log.i("makis", "list.size ${list.size}")
            (binding.recyclerView.adapter as TripsInvolvedAdapter).submitList(null)
            (binding.recyclerView.adapter as TripsInvolvedAdapter).submitList(list)
            binding.swipeRefreshLayout.isRefreshing = false

            //Check if we have active trips
            if (list.isNullOrEmpty())
                binding.offersSection.visibility = View.VISIBLE
            else
                binding.offersSection.visibility = View.GONE
        })

        viewModel.loadCreatorActive.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.offersSection.visibility = View.GONE
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
            }
        })*/

    }

    override fun setUpViews() {
        // viewModel.loadTripsAsCreator()
        /*binding.recyclerView.adapter = TripsInvolvedAdapter(R.drawable.background_stroke_radius_27_green, this@TripsActiveTabFragment::onTripClick)


        binding.buttonHistory.setOnClickListener {
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


        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as TripsInvolvedAdapter).submitList(null)
            viewModel.loadTripsAsCreator(true)
        }*/
    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(
            R.id.action_destination_trips_to_tripInvolvedDetailsFragment, bundleOf("tripId" to tripInvolved.id, "reload" to false, "history" to false, "creator" to true)
        )
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
    }


}


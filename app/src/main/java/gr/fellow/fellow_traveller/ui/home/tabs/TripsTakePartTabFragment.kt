package gr.fellow.fellow_traveller.ui.home.tabs

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTakesPartTabBinding
import gr.fellow.fellow_traveller.domain.TripType
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.startActivity
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.search.SearchTripActivity

@AndroidEntryPoint
class TripsTakePartTabFragment : BaseFragment<FragmentTakesPartTabBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var tripsList = mutableListOf<TripInvolved>()


    override fun getViewBinding(): FragmentTakesPartTabBinding =
        FragmentTakesPartTabBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        //homeViewModel.loadTripTakesPart()
        homeViewModel.tripsTakesPart.observe(viewLifecycleOwner, Observer { list ->
            tripsList.clear()
            tripsList.addAll(list)

            if (tripsList.isNotEmpty()) {
                setTripInfo()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.noTripsSection.visibility = View.VISIBLE
            }
        })
    }

    override fun setUpViews() {
        binding.noTripsSectionButtonLayout.setOnClickListener {
            startActivity(SearchTripActivity::class)
        }
    }


    private fun setTripInfo() {

        with(binding) {
            val trip = tripsList.first()
            destinationsFromTextView.text = trip.destFrom.title
            destinationsToTextView.text = trip.destTo.title
            creatorName.text = trip.creatorUser.fullName
            date.text = trip.date
            time.text = trip.time
            price.text = trip.price.toString()
            creatorImage.loadImageFromUrl(trip.creatorUser.picture)
            creatorRateAndReviews.text = "${trip.creatorUser.rate} (${trip.creatorUser.reviews})"
            numOfActiveTripsTextView.text = "${tripsList.size} ενεργά ταξίδια"
            progressBar.visibility = View.GONE
            activeTripsSection.visibility = View.VISIBLE

            buttonAllActiveTrips.setOnClickListener {
                findNavController()?.navigate(
                    R.id.action_destination_trips_takes_part_to_offersActiveTripsFragment,
                    bundleOf("type" to TripType.TakesPart.name)
                )
            }
        }
    }


}
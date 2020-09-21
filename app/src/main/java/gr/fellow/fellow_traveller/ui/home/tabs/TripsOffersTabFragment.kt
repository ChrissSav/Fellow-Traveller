package gr.fellow.fellow_traveller.ui.home.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.databinding.FragmentTripsOffersBinding
import gr.fellow.fellow_traveller.domain.TripType
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity


class TripsOffersTabFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentTripsOffersBinding? = null
    private val binding get() = _binding!!
    private var tripsList = mutableListOf<Trip>()
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripsOffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        homeViewModel.loadTripAsCreator()
        homeViewModel.tripsAsCreator.observe(viewLifecycleOwner, Observer { list ->
            tripsList.clear()
            tripsList.addAll(list)

            if (tripsList.isNotEmpty()) {
                setTripInfo()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.noTripsSection.visibility = View.VISIBLE
            }
        })

        binding.noTripsSectionButtonLayout.setOnClickListener {
            val intent = Intent(activity, NewTripActivity::class.java)
            startActivity(intent)
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
                navController.navigate(R.id.action_destination_trips_to_offersActiveTripsFragment, bundleOf("type" to TripType.Offer.name))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
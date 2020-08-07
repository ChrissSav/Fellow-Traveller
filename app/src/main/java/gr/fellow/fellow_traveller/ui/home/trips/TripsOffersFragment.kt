package gr.fellow.fellow_traveller.ui.home.trips

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.databinding.FragmentTripsOffersBinding
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TripsOffersFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentTripsOffersBinding? = null
    private val binding get() = _binding!!
    private var tripsList = mutableListOf<TripResponse>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripsOffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.loadTripAsCreator()
        homeViewModel.tripsAsCreator.observe(viewLifecycleOwner, Observer { list ->

            tripsList = list.sortedWith(compareBy { it.timestamp }).toMutableList()

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
            date.text = trip.getDate()
            time.text = trip.getTime()
            price.text = trip.price.toString()
            creatorImage.loadImageFromUrl(trip.creatorUser.picture)
            creatorRateAndReviews.text = "${trip.creatorUser.rate} (${trip.creatorUser.reviews})"
            numOfActiveTripsTextView.text = "${tripsList.size} ενεργά ταξίδια"
            progressBar.visibility = View.GONE
            activeTripsSection.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
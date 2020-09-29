package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.models.UserBase
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripDetailsBinding
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter


class SearchTripDetailsFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    private var _binding: FragmentSearchTripDetailsBinding? = null
    private val binding get() = _binding!!

    private var tripId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripId = requireArguments().getInt("tripId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchTripDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        val index = searchTripViewModel.resultTrips.value?.indexOfFirst { it.id == tripId }!!

        searchTripViewModel.resultTrips.value?.get(index)?.let {
            with(it) {
                binding.userImage.loadImageFromUrl(creatorUser.picture)
                binding.userName.text = creatorUser.fullName
                binding.ratingTv.text = creatorUser.rate.toString()
                binding.reviewsTv.text = creatorUser.reviews.toString()
                binding.fromDestTv.text = destFrom.title
                binding.toDestTv.text = destTo.title
                binding.dateTv.text = time
                binding.timeTv.text = date
                binding.bagsTv.text = bagsState
                binding.seatsTv.text = seatsState
                binding.petsTv.text = if (hasPet) resources.getString(R.string.allowed) else resources.getString(R.string.not_allowed)
                binding.carTv.text = "${carBase.brand} ,${carBase.model}"
                binding.pickUpPointInfo.text = pickupPoint.title
                binding.priceTv.text = getString(R.string.price, price.toString())
                binding.driverMessageTv.text = msg ?: resources.getString(R.string.no_driver_message)


                binding.bookButton.setOnClickListener {
                    navController.navigate(
                        R.id.action_searchTripDetailsFragment_to_bookTripFragment,
                        bundleOf("indexTrip" to index)
                    )
                }

                if (!passengers.isNullOrEmpty()) {
                    binding.passengerRecyclerView.adapter = PassengerAdapter(passengers, this@SearchTripDetailsFragment::onPassengerListener)
                }

            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }


    }

    private fun onPassengerListener(user: UserBase) {
        Toast.makeText(this.context, user.id.toString().trim(), Toast.LENGTH_SHORT).show()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
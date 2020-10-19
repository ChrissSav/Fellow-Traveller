package gr.fellow.fellow_traveller.ui.search.fragments

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter

@AndroidEntryPoint
class SearchTripDetailsFragment : BaseFragment<FragmentSearchTripDetailsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripId: Int = 0


    override fun handleIntent() {
        tripId = requireArguments().getInt("tripId")
    }

    override fun getViewBinding(): FragmentSearchTripDetailsBinding =
        FragmentSearchTripDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        val index = viewModel.resultTrips.value?.indexOfFirst { it.id == tripId }!!

        viewModel.resultTrips.value?.get(index)?.let {
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
                    findNavController()?.navigate(
                        R.id.action_searchTripDetailsFragment_to_bookTripFragment,
                        bundleOf("indexTrip" to index)
                    )
                }

                if (!passengers.isNullOrEmpty()) {
                    binding.passengerRecyclerView.adapter = PassengerAdapter(passengers, this@SearchTripDetailsFragment::onPassengerListener)
                }

            }
        }
    }

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }


    private fun onPassengerListener(user: UserBase) {
        Toast.makeText(this.context, user.id.toString().trim(), Toast.LENGTH_SHORT).show()

    }


}
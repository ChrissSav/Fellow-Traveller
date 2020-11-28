package gr.fellow.fellow_traveller.ui.search.fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter
import gr.fellow.fellow_traveller.ui.user.UserProfileDetailsActivity


@AndroidEntryPoint
class SearchTripDetailsFragment : BaseFragment<FragmentSearchTripDetailsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripId = "0"
    private var index: Int? = null


    override fun handleIntent() {
        tripId = requireArguments().getString("tripId").toString()
    }

    override fun getViewBinding(): FragmentSearchTripDetailsBinding =
        FragmentSearchTripDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        index = viewModel.resultTrips.value?.indexOfFirst { it.id == tripId }
        if (index != null) {
            viewModel.resultTrips.value?.get(index!!)?.let { trip ->
                with(binding) {
                    from.text = trip.destFrom.title
                    to.text = trip.destTo.title
                    time.text = trip.time
                    day.text = trip.date
                    userImage.loadImageFromUrl(trip.creatorUser.picture)
                    username.text = trip.creatorUser.fullName
                    rate.text = trip.creatorUser.rate.toString()
                    price.text = resources.getString(R.string.price, trip.price.toString())
                    seats.text = trip.seatsStatus
                    bags.text = trip.bags
                    pet.text = if (trip.hasPet) resources.getString(R.string.yes) else resources.getString(R.string.no)
                    car.text = trip.carBase.baseInfo
                    message.text = if (trip.msg.isNullOrEmpty()) resources.getString(R.string.no_driver_message) else trip.msg


                    //Delete the "ς" or "s" when we display the name of the creator in the messenger button
                    if (trip.creatorUser.firstName.takeLast(1).equals("ς"))
                        messengerLinkText.text = getString(R.string.send_message_to, trip.creatorUser.firstName.dropLast(1))
                    else
                        messengerLinkText.text = getString(R.string.send_message_to, trip.creatorUser.firstName)



                    googleMaps.setOnClickListener {
                        activity?.openGoogleMaps(trip)
                    }

                    userImage.setOnClickListener {
                        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to trip.creatorUser.id))
                    }

                    messengerLinkConstraintLayout.setOnClickListener {

                    }


                    description.text = "${price.text} " + getString(R.string.per_person)

                    if (!trip.passengers.isNullOrEmpty()) {
                        binding.passengersSection.visibility = View.GONE
                        binding.passengerRecyclerView.adapter = PassengerAdapter(trip.passengers, this@SearchTripDetailsFragment::onPassengerListener)
                    } else {
                        binding.passengersSection.visibility = View.VISIBLE
                    }


                    messengerLinkConstraintLayout.setOnClickListener {
                        activity?.openMessenger(trip.creatorUser.messengerLink)
                    }

                }
            }
        } else {
            onBackPressed()
        }
    }

    override fun setUpViews() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }



        binding.constraintLayoutMessenger.setOnClickListener {
            findNavController()?.navigate(R.id.action_searchTripDetailsFragment_to_bookTripFragment, bundleOf("index" to index))
        }

        binding.bookTrip.setOnClickListener {
            findNavController()?.navigate(R.id.action_searchTripDetailsFragment_to_bookTripFragment, bundleOf("index" to index))
        }
    }


    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
    }


}

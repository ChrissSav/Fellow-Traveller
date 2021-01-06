package gr.fellow.fellow_traveller.ui.search.fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
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

    private val args: SearchTripDetailsFragmentArgs by navArgs()
    private val viewModel: SearchTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentSearchTripDetailsBinding =
        FragmentSearchTripDetailsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        val trip = args.trip

        if (trip != null) {

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
                bags.text = getString(trip.bags.textInt)
                pet.text = if (trip.hasPet) resources.getString(R.string.yes) else resources.getString(R.string.no)
                car.text = trip.carBase.baseInfo
                message.setText(trip.msg)
                description.text = "${price.text} ${getString(R.string.per_person)}"


                googleMaps.setOnClickListener {
                    activity?.openGoogleMaps(trip)
                }

                userImage.setOnClickListener {
                    activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to trip.creatorUser.id))
                }


                if (!trip.passengers.isNullOrEmpty()) {
                    binding.passengersSection.visibility = View.GONE
                    binding.passengerRecyclerView.adapter = PassengerAdapter(trip.passengers, this@SearchTripDetailsFragment::onPassengerListener)
                } else {
                    binding.passengersSection.visibility = View.VISIBLE
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
            findNavController()?.navigate(R.id.action_searchTripDetailsFragment_to_bookTripFragment, bundleOf("trip" to args.trip))
        }

        binding.bookTrip.setOnClickListener {
            findNavController()?.navigate(R.id.action_searchTripDetailsFragment_to_bookTripFragment, bundleOf("trip" to args.trip))
        }
    }


    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
    }


}

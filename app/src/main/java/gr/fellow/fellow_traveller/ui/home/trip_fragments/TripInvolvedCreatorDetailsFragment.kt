package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedCreatorDetailsBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.ConfirmBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter
import gr.fellow.fellow_traveller.ui.user.UserProfileDetailsActivity
import kotlinx.android.synthetic.main.notification_item_layout.*

@AndroidEntryPoint
class TripInvolvedCreatorDetailsFragment : BaseFragment<FragmentTripInvolvedCreatorDetailsBinding>() {

    private val args: TripInvolvedCreatorDetailsFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var confirmBottomSheetDialog: ConfirmBottomSheetDialog


    override fun getViewBinding(): FragmentTripInvolvedCreatorDetailsBinding =
        FragmentTripInvolvedCreatorDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.successDeletion.observe(viewLifecycleOwner, Observer {
            createToast(getString(it))
            onBackPressed()

        })

        viewModel.loadTripInvolvedDetails.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
            }
        })

        viewModel.tripInvolvedDetails.observe(viewLifecycleOwner, Observer { trip ->

            with(binding) {

                tripDetailConstraintLayout.visibility = View.VISIBLE
                constraintLayoutInfo.visibility = View.VISIBLE

                if (trip.status != TripStatus.ACTIVE.code)
                    delete.visibility = View.INVISIBLE
                else
                    delete.visibility = View.VISIBLE


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
                car.text = trip.car.fullInfo
                message.setText(trip.msg)



                if (!trip.passengers.isNullOrEmpty()) {
                    binding.viewAllPassengers.visibility = View.VISIBLE
                    binding.passengersSection.visibility = View.GONE
                    binding.passengerRecyclerView.adapter = PassengerAdapter(trip.passengers, this@TripInvolvedCreatorDetailsFragment::onPassengerListener)
                } else {
                    binding.passengersSection.visibility = View.VISIBLE
                    binding.viewAllPassengers.visibility = View.GONE
                }

                description.setTextHtml(getString(R.string.trip_involved_description, getTripStatus(trip.status)))



                googleMaps.setOnClickListener {
                    activity?.openGoogleMaps(trip)
                }

                viewAllPassengers.setOnClickListener {
                    findNavController()?.navigate(
                        R.id.action_tripInvolvedDetailsFragment_to_tripInvolvedPassengerDetailsFragment,
                        bundleOf("passengerList" to trip.passengers.toTypedArray())
                    )
                }

                backButton.setOnClickListener {
                    onBackPressed()
                }

                delete.setOnClickListener {
                    confirmBottomSheetDialog = ConfirmBottomSheetDialog(
                        getString(R.string.delete_trip_question),
                        this@TripInvolvedCreatorDetailsFragment::onConfirmItemClickListener, 1
                    )
                    confirmBottomSheetDialog.show(childFragmentManager, "confirmBottomSheetDialog")

                }


                userImage.setOnClickListener {
                    val userId = viewModel.user.value?.id
                    if (trip.creatorUser.id != userId)
                        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to trip.creatorUser.id))
                    else {
                        findNavController()?.navigate(R.id.action_tripInvolvedDetailsFragment_to_destination_info)
                    }
                }
            }

        })
    }


    override fun setUpViews() {
        if (args.history)
            viewModel.loadTripCreatorHistoryInvolvedDetails(args.tripId, args.reload)
        else
            viewModel.loadTripCreatorActiveInvolvedDetails(args.tripId, args.reload)
    }

    private fun onPassengerListener(user: UserBase) {
        val userId = viewModel.user.value?.id
        if (user.id != userId)
            activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
        else {
            findNavController()?.navigate(R.id.action_tripInvolvedDetailsFragment_to_destination_info)
        }
    }


    private fun onConfirmItemClickListener(answerType: AnswerType) {
        if (answerType == AnswerType.Yes) {
            viewModel.deleteTrip(args.tripId)
        }
    }


    private fun getTripStatus(status: Int) =
        when (status) {
            TripStatus.ACTIVE.code -> getString(R.string.trip_status_active)
            TripStatus.PENDING.code -> getString(R.string.trip_status_in_progress)
            else -> getString(R.string.trip_status_complete)
        }

}
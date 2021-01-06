package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.discord.panels.OverlappingPanelsLayout
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedDetailsBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.mappers.mapToUserBase
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.ConfirmBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapterVertical
import gr.fellow.fellow_traveller.ui.user.UserProfileDetailsActivity

@AndroidEntryPoint
class TripInvolvedDetailsFragment : BaseFragment<FragmentTripInvolvedDetailsBinding>() {

    private val args: TripInvolvedDetailsFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var confirmBottomSheetDialog: ConfirmBottomSheetDialog
    private var userId = ""


    override fun getViewBinding(): FragmentTripInvolvedDetailsBinding =
        FragmentTripInvolvedDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.successDeletion.observe(viewLifecycleOwner, {
            createToast(getString(it))
            onBackPressed()

        })

        viewModel.user.observe(viewLifecycleOwner, {
            userId = it.id
        })

        viewModel.loadTripInvolvedDetails.observe(viewLifecycleOwner, {
            if (it) {
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
            }
        })

        viewModel.tripInvolvedDetails.observe(viewLifecycleOwner, { trip ->

            with(binding) {

                overlappingPanels.setEndPanelLockState(OverlappingPanelsLayout.LockState.UNLOCKED)

                secondLayout(trip)
                tripDetailConstraintLayout.visibility = View.VISIBLE
                constraintLayoutInfo.visibility = View.VISIBLE

                if (trip.status != TripStatus.ACTIVE)
                    moreInfoLayout.exit.visibility = View.GONE
                else
                    moreInfoLayout.exit.visibility = View.VISIBLE


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
                    binding.passengerRecyclerView.adapter = PassengerAdapter(trip.passengers, this@TripInvolvedDetailsFragment::onPassengerListener)
                } else {
                    binding.passengersSection.visibility = View.VISIBLE
                    binding.viewAllPassengers.visibility = View.GONE
                }

                description.setTextHtml(getString(R.string.trip_involved_description, getString(trip.status.textInt)))

                if (trip.creatorUser.firstName.takeLast(1) == "ς")
                    messengerLinkText.text = getString(R.string.send_message_to, trip.creatorUser.firstName.dropLast(1))
                else
                    messengerLinkText.text = getString(R.string.send_message_to, trip.creatorUser.firstName)


                googleMaps.setOnClickListener {
                    activity?.openGoogleMaps(trip)
                }


                viewAllPassengers.setOnClickListener {
                    binding.overlappingPanels.openEndPanel()
                }


                userImage.setOnClickListener {
                    val userId = viewModel.user.value?.id
                    if (trip.creatorUser.id != userId)
                        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to trip.creatorUser.id))
                    else {
                        findNavController()?.navigate(R.id.action_tripInvolvedCreatorDetailsFragment_to_destination_info)
                    }
                }

                messengerLinkConstraintLayout.setOnClickListener {
                    activity?.openMessenger(trip.creatorUser.messengerLink)
                }
                messengerLinkText.setOnClickListener {
                    activity?.openMessenger(trip.creatorUser.messengerLink)
                }


            }

        })
    }


    override fun setUpViews() {

        binding.overlappingPanels.setStartPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)
        binding.overlappingPanels.setEndPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)


        if (args.creator) {
            binding.moreInfoLayout.deleteTextView.text = getString(R.string.delete)
            binding.messengerLinkConstraintLayout.visibility = View.GONE
            binding.constraintLayoutInfo.backgroundTintList = ContextCompat.getColorStateList(binding.constraintLayoutInfo.context, R.color.green)
            binding.labelDescription.text = getString(R.string.youre_trip_driver)
            if (args.history)
                viewModel.loadTripCreatorHistoryInvolvedDetails(args.tripId, args.reload, args.notificationId)
            else
                viewModel.loadTripCreatorActiveInvolvedDetails(args.tripId, args.reload, args.notificationId)
        } else {
            binding.moreInfoLayout.deleteTextView.text = getString(R.string.cancel)
            binding.constraintLayoutInfo.backgroundTintList = ContextCompat.getColorStateList(binding.constraintLayoutInfo.context, R.color.orange_new)
            binding.labelDescription.text = getString(R.string.you_have_booked_this_trip)
            binding.messengerLinkConstraintLayout.visibility = View.VISIBLE
            if (args.history)
                viewModel.loadTripTakesPartHistoryInvolvedDetails(args.tripId, args.reload, args.notificationId)
            else
                viewModel.loadTripTakesPartActiveInvolvedDetails(args.tripId, args.reload, args.notificationId)
        }


        binding.backButton.setOnClickListener {
            onBackPressed()
        }




        binding.moreInfo.setOnClickListener {
            binding.overlappingPanels.openEndPanel()
        }

    }


    private fun secondLayout(trip: TripInvolved) {

        with(binding.moreInfoLayout) {
            driverImage.loadImageFromUrl(trip.creatorUser.picture)
            driverName.text = trip.creatorUser.firstName
            passengers.adapter = PassengerAdapterVertical(trip.passengers, this@TripInvolvedDetailsFragment::onPassengerListener)

            constraintLayoutDriver.setOnClickListener {
                onPassengerListener(trip.creatorUser.mapToUserBase())
            }

            conversationButton.setOnClickListener {
                findNavController()?.navigateWithFade(R.id.destination_messenger)
            }

            exitButton.setOnClickListener {
                ExitCustomDialog(
                    requireActivity(), this@TripInvolvedDetailsFragment::onConfirmItemClickListener,
                    if (args.creator) getString(R.string.delete_trip_question) else getString(R.string.leave_trip_question),
                    1
                ).show(childFragmentManager, "exitCustomDialog")
            }

        }


    }

    private fun onPassengerListener(user: UserBase) {
        val userId = viewModel.user.value?.id
        if (user.id != userId)
            activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
        else {

        }
    }


    private fun onConfirmItemClickListener(answerType: AnswerType) {
        if (answerType == AnswerType.Yes) {
            if (args.creator)
                viewModel.deleteTrip(args.tripId)
            else
                viewModel.exitFromTrip(args.tripId)
        }
    }
}
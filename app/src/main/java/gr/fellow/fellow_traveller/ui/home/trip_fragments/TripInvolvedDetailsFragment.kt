package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedDetailsBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.loadDestination
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.openGoogleMaps
import gr.fellow.fellow_traveller.ui.extensions.setDestination
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.utils.convertTimestampToFormat

@AndroidEntryPoint
class TripInvolvedDetailsFragment : BaseFragment<FragmentTripInvolvedDetailsBinding>() {

    private val args: TripInvolvedDetailsFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var tripInvolved: TripInvolved
    private var userId = ""


    override fun getViewBinding(): FragmentTripInvolvedDetailsBinding =
        FragmentTripInvolvedDetailsBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun setUpObservers() {

        /*viewModel.successDeletion.observe(viewLifecycleOwner) {
            createToast(getString(it))
            onBackPressed()

        }

        viewModel.user.observe(viewLifecycleOwner) {
            userId = it.id
        }

        viewModel.loadTripInvolvedDetails.observe(viewLifecycleOwner) {
            if (it) {
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
            }
        }

        viewModel.tripInvolvedDetails.observe(viewLifecycleOwner) { trip ->

            tripInvolved = trip
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
                // seats.text = trip.seatsStatus
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

            }

        }*/

        viewModel.tripInvolvedDetails.observe(viewLifecycleOwner) { trip ->

            with(binding) {

                destinationFrom.setDestination(trip.destFrom)
                destinationTo.setDestination(trip.destTo)

                pet.visibility = if (trip.hasPet) View.VISIBLE else View.GONE

                date.text = convertTimestampToFormat(trip.timestamp, "EEE dd MMM, HH:mm")
                price.text = getString(R.string.price, trip.price.toString())
                seats.text = "${trip.seatAvailable}/${trip.seats}"
                bags.text = getString(trip.bags.textInt)

                destinationPickUp.text = trip.destPickUp.title.replace("$$", " ")

                car.text = trip.car.baseInfo

                pickUpImage.loadDestination(trip.destPickUp)

                binding.openInMaps.setOnClickListener {
                    activity?.openGoogleMaps(trip.destPickUp)
                }
            }

        }
    }


    override fun setUpViews() {

        binding.back.button.setOnClickListener {
            onBackPressed()
        }



        if (!args.history) {
            viewModel.loadTripActiveInvolvedDetails(args.tripId, args.reload, args.notificationId)
        }
        /* binding.overlappingPanels.setStartPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)
         binding.overlappingPanels.setEndPanelLockState(OverlappingPanelsLayout.LockState.CLOSE)


         if (args.creator) {
             binding.moreInfoLayout.deleteTextView.text = getString(R.string.delete)
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
             if (args.history)
                 viewModel.loadTripTakesPartHistoryInvolvedDetails(args.tripId, args.reload, args.notificationId)
             else
                 viewModel.loadTripTakesPartActiveInvolvedDetails(args.tripId, args.reload, args.notificationId)
         }


         binding.moreInfo.setOnClickListener {
             binding.overlappingPanels.openEndPanel()
         }*/

    }


    /* private fun secondLayout(trip: TripInvolved) {

         with(binding.moreInfoLayout) {
             driverImage.loadImageFromUrl(trip.creatorUser.picture)
             driverName.text = trip.creatorUser.fullName
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

             conversationButton.setOnClickListener {
                 if (tripInvolved.passengers.isNotEmpty())
                     findNavController()?.navigate(R.id.action_tripInvolvedDetailsSecondFragment_to_chatFragment,
                         bundleOf("conversationItem" to Conversation(tripInvolved.id, tripInvolved.placesInfo, "",
                             0, "", false)))
                 else
                     findNavController()?.navigate(R.id.action_tripInvolvedDetailsSecondFragment_to_destination_messenger)
             }


             if (trip.passengers.isNotEmpty()) {
                 passengersSection.visibility = View.GONE
             } else {
                 passengersSection.visibility = View.VISIBLE
             }


         }


     }

     private fun onPassengerListener(user: UserBase) {
         val userId = viewModel.user.value?.id
         if (user.id != userId)
             activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
         else {
             findNavController()?.navigate(R.id.action_tripInvolvedCreatorDetailsFragment_to_destination_info)
         }
     }


     private fun onConfirmItemClickListener(answerType: AnswerType) {
         if (answerType == AnswerType.Yes) {

             val tempIdList = mutableListOf<String>()
             tripInvolved.passengers.forEach {
                 tempIdList.add(it.user.id)
             }
             tempIdList.add(tripInvolved.creatorUser.id)

             if (args.creator)
                 viewModel.deleteTrip(args.tripId, tempIdList)
             else {

                 viewModel.exitFromTrip(args.tripId, tempIdList)
             }

         }
     }*/
}
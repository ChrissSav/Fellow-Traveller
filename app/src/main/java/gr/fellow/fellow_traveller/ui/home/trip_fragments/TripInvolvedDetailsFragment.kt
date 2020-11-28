package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedDetailsBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.ConfirmBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter
import gr.fellow.fellow_traveller.ui.user.UserProfileDetailsActivity

@AndroidEntryPoint
class TripInvolvedDetailsFragment : BaseFragment<FragmentTripInvolvedDetailsBinding>() {

    private val args: TripInvolvedDetailsFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var confirmBottomSheetDialog: ConfirmBottomSheetDialog
    private var creator = false
    private var currentTrip: TripInvolved? = null

    override fun getViewBinding(): FragmentTripInvolvedDetailsBinding =
        FragmentTripInvolvedDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.successDeletion.observe(viewLifecycleOwner, Observer {
            createToast(getString(it))
            onBackPressed()
        })
    }

    private fun loadTrip() {

        Log.i("args.tripId", args.tripId)
        try {
            currentTrip = viewModel.tripsAsCreatorActive.value?.first { it.id == args.tripId }
        } catch (e: NoSuchElementException) {
        }

        try {
            if (currentTrip == null)
                currentTrip = viewModel.tripsAsCreatorHistory.value?.first { it.id == args.tripId }
        } catch (e: NoSuchElementException) {
        }
        try {
            if (currentTrip == null)
                currentTrip = viewModel.tripsAsPassengerActive.value?.first { it.id == args.tripId }
        } catch (e: NoSuchElementException) {
        }
        try {
            if (currentTrip == null)
                currentTrip = viewModel.tripsAsPassengerHistory.value?.first { it.id == args.tripId }
        } catch (e: NoSuchElementException) {
        }

        if (currentTrip == null)
            onBackPressed()
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun setUpViews() {
        loadTrip()

        currentTrip?.let { trip ->
            if (trip.status != 0)
                binding.delete.visibility = View.INVISIBLE

            binding.description.setTextHtml(getString(R.string.trip_involved_description, getTripStatus(trip.status)))

            creator = !trip.passengers.filter { it.user.id == viewModel.user.value?.id.toString() }.any()

            if (creator) {
                binding.constraintLayoutInfo.backgroundTintList = ContextCompat.getColorStateList(binding.constraintLayoutInfo.context, R.color.green)
                binding.labelDescription.text = getString(R.string.youre_trip_driver)
            }

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
                car.text = trip.car.fullInfo
                message.text = if (trip.msg.isNullOrEmpty()) resources.getString(R.string.no_driver_message) else trip.msg
                messengerLinkText.text = getString(R.string.send_message_to, trip.creatorUser.firstName)

                if (!trip.passengers.isNullOrEmpty()) {
                    binding.viewAllPassengers.visibility = View.VISIBLE
                    binding.passengersSection.visibility = View.GONE
                    binding.passengerRecyclerView.adapter = PassengerAdapter(trip.passengers, this@TripInvolvedDetailsFragment::onPassengerListener)
                } else {
                    binding.passengersSection.visibility = View.VISIBLE
                    binding.viewAllPassengers.visibility = View.GONE
                }

            }

            binding.viewAllPassengers.setOnClickListener {
                findNavController()?.navigate(
                    R.id.action_tripInvolvedDetailsFragment_to_tripInvolvedPassengerDetailsFragment,
                    bundleOf("passengerList" to trip.passengers.toTypedArray())
                )
            }

            binding.backButton.setOnClickListener {
                onBackPressed()
            }

            binding.delete.setOnClickListener {
                confirmBottomSheetDialog = ConfirmBottomSheetDialog(
                    if (creator) getString(R.string.delete_trip_question) else getString(R.string.leave_trip_question),
                    this@TripInvolvedDetailsFragment::onConfirmItemClickListener, 1
                )
                confirmBottomSheetDialog.show(childFragmentManager, "confirmBottomSheetDialog")

            }

            binding.messengerLinkConstraintLayout.setOnClickListener {
                activity?.openMessenger(trip.creatorUser.messengerLink)
            }

            binding.userImage.setOnClickListener {
                activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to trip.creatorUser.id))
            }

            binding.googleMaps.setOnClickListener {
                activity?.openGoogleMaps(trip)
            }
        }

    }

    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
    }


    private fun onConfirmItemClickListener(answerType: AnswerType) {
        currentTrip?.let {
            if (answerType == AnswerType.Yes) {
                if (creator)
                    viewModel.deleteTrip(it.id)
                else
                    viewModel.exitFromTrip(it.id)
            }
        }
    }


    private fun getTripStatus(status: Int) =
        when (status) {
            0 -> getString(R.string.trip_status_active)
            1 -> getString(R.string.trip_status_in_progress)
            else -> getString(R.string.trip_status_complete)
        }

}
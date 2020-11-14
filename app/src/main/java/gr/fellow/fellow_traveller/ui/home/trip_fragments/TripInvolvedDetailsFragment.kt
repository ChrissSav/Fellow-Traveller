package gr.fellow.fellow_traveller.ui.home.trip_fragments

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedDetailsBinding
import gr.fellow.fellow_traveller.domain.AnswerType
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

    override fun getViewBinding(): FragmentTripInvolvedDetailsBinding =
        FragmentTripInvolvedDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.successDeletion.observe(viewLifecycleOwner, Observer {
            createToast(it)
            onBackPressed()
        })
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun setUpViews() {

        binding.description.setTextHtml(getString(R.string.trip_involved_description, getTripStatus(args.trip.status)))

        if (args.creator) {
            binding.constraintLayoutInfo.backgroundTintList = resources.getColorStateList(R.color.aqua)
            binding.labelDescription.text = "Είσαι ο οδηγός του ταξιδιού"
        }

        with(binding) {
            from.text = args.trip.destFrom.title
            to.text = args.trip.destTo.title
            time.text = args.trip.time
            day.text = args.trip.date
            userImage.loadImageFromUrl(args.trip.creatorUser.picture)
            username.text = args.trip.creatorUser.fullName
            rate.text = args.trip.creatorUser.rate.toString()
            price.text = resources.getString(R.string.price, args.trip.price.toString())
            seats.text = args.trip.seatsStatus
            bags.text = args.trip.bags
            pet.text = if (args.trip.hasPet) resources.getString(R.string.yes) else resources.getString(R.string.no)
            car.text = args.trip.car.fullInfo
            message.text = if (args.trip.msg.isNullOrEmpty()) resources.getString(gr.fellow.fellow_traveller.R.string.no_driver_message) else args.trip.msg


            if (!args.trip.passengers.isNullOrEmpty()) {
                binding.viewAllPassengers.visibility = View.VISIBLE
                binding.passengerRecyclerView.adapter = PassengerAdapter(args.trip.passengers, this@TripInvolvedDetailsFragment::onPassengerListener)
            }

        }

        binding.viewAllPassengers.setOnClickListener {
            findNavController()?.navigate(R.id.action_tripInvolvedDetailsFragment_to_tripInvolvedPassengerDetailsFragment, bundleOf("passengerList" to args.trip.passengers.toTypedArray()))
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.moreSettings.setOnClickListener {
            confirmBottomSheetDialog = ConfirmBottomSheetDialog(
                if (args.creator) "Θελείς να διαγράψεις το ταξίδι;" else "Θέλεις να αποχωρήσεις απο το ταξίδι;",
                this@TripInvolvedDetailsFragment::onConfirmItemClickListener
            )
            confirmBottomSheetDialog.show(childFragmentManager, "confirmBottomSheetDialog")

        }

        binding.userImage.setOnClickListener {
            activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to args.trip.creatorUser.id))
        }

        binding.googleMaps.setOnClickListener {
            activity?.openGoogleMaps(args.trip)
        }
    }

    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserProfileDetailsActivity::class, bundleOf("userId" to user.id))
    }


    private fun onConfirmItemClickListener(answerType: AnswerType) {
        if (answerType == AnswerType.Yes) {
            if (args.creator)
                viewModel.deleteTrip(args.trip.id)
            else
                viewModel.exitFromTrip(args.trip.id)
        }
    }


    private fun getTripStatus(status: Int) =
        when (status) {
            0 -> "Ενεργό"
            1 -> "Εκκρεμεί"
            else -> "Ολοκληρωμένο"
        }

}
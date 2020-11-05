package gr.fellow.fellow_traveller.ui.home.trip

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
import gr.fellow.fellow_traveller.ui.user.UserInfoDetailsActivity

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

        if (args.creator) {
            binding.constraintLayoutInfo.backgroundTintList = resources.getColorStateList(R.color.aqua)
            binding.label.text = "Είσαι ο οδηγός του ταξιδιού"
            binding.description.text = "Επεξεργάσου το ταξίδι"
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
            message.text = args.trip.msg ?: resources.getString(R.string.no_driver_message)


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
                if (args.creator) "Θελείς να διαγράψεις την διαδρομή ;" else "Θέλεις να αποχωρήσεις απο την διαδρομή ;",
                this@TripInvolvedDetailsFragment::onConfirmItemClickListener
            )
            confirmBottomSheetDialog.show(childFragmentManager, "confirmBottomSheetDialog")

        }

        binding.userImage.setOnClickListener {
            activity?.startActivityWithBundle(UserInfoDetailsActivity::class, bundleOf("userId" to args.trip.creatorUser.id))
        }
    }

    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserInfoDetailsActivity::class, bundleOf("userId" to user.id))
    }


    private fun onConfirmItemClickListener(answerType: AnswerType) {
        if (answerType == AnswerType.Yes) {
            if (args.creator)
                viewModel.deleteTrip(args.trip.id)
            else
                viewModel.exitFromTrip(args.trip.id)
        }
    }


}
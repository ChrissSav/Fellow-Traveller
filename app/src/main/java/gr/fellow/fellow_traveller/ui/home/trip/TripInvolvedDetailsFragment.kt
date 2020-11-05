package gr.fellow.fellow_traveller.ui.home.trip

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedDetailsBinding
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.startActivityWithBundle
import gr.fellow.fellow_traveller.ui.search.adapter.PassengerAdapter
import gr.fellow.fellow_traveller.ui.user.UserInfoDetailsActivity


class TripInvolvedDetailsFragment : BaseFragment<FragmentTripInvolvedDetailsBinding>() {

    private val args: TripInvolvedDetailsFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentTripInvolvedDetailsBinding =
        FragmentTripInvolvedDetailsBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

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


        binding.userImage.setOnClickListener {
            activity?.startActivityWithBundle(UserInfoDetailsActivity::class, bundleOf("userId" to args.trip.creatorUser.id))
        }
    }

    private fun onPassengerListener(user: UserBase) {
        activity?.startActivityWithBundle(UserInfoDetailsActivity::class, bundleOf("userId" to user.id))
    }


}
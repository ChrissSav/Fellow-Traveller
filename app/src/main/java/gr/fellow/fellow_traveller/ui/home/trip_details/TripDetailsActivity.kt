package gr.fellow.fellow_traveller.ui.home.trip_details

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityTripDetailsBinding
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.loadDestinationImage
import gr.fellow.fellow_traveller.ui.extensions.openGoogleMaps
import gr.fellow.fellow_traveller.ui.extensions.setDestination
import gr.fellow.fellow_traveller.utils.convertTimestampToFormat

@AndroidEntryPoint
class TripDetailsActivity : BaseActivityViewModel<ActivityTripDetailsBinding, TripDetailsViewModel>(TripDetailsViewModel::class.java) {

    private var tripId: String? = null
    private var status: TripStatus? = null
    private var currentTrip: TripInvolved? = null


    override fun handleIntent() {

        currentTrip = intent.getParcelableExtra("trip")

        if (currentTrip == null) {
            status = intent.getParcelableExtra("status")
            tripId = intent.getStringExtra("tripId")

            if (tripId == null || status == null)
                finish()

        } else {
            status = currentTrip?.status
            tripId = currentTrip?.id
        }

    }


    override fun provideViewBinding(): ActivityTripDetailsBinding =
        ActivityTripDetailsBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun setUpObservers() {

        viewModel.tripUser.observe(this, Observer {
            val trip = it.first

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

                pickUpImage.loadDestinationImage(trip.destPickUp, if (it.first.creatorUser.id == it.second.id) getString(R.color.green_new) else getString(R.color.orange_new))

                binding.openInMaps.setOnClickListener {
                    openGoogleMaps(trip.destPickUp)
                }
            }
        })
    }

    override fun setUpViews() {

        binding.back.button.setOnClickListener {
            finish()
        }


        if (currentTrip == null) {

        } else {
            viewModel.setTrip(currentTrip!!)
        }
    }
}
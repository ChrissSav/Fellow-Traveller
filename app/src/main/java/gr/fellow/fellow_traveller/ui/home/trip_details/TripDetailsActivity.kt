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
import gr.fellow.fellow_traveller.ui.extensions.loadDirectionsImage
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.onClickListener
import gr.fellow.fellow_traveller.ui.extensions.openGoogleMaps
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

                destinationFromAdministrative.text = trip.destFrom.administrative?.title
                destinationFromDes.text = trip.destFrom.title
                destinationToAdministrative.text = trip.destTo.administrative?.title
                destinationToDes.text = trip.destTo.title

                pet.visibility = if (trip.hasPet) View.VISIBLE else View.GONE

                date.text = convertTimestampToFormat(trip.timestamp, "EEE dd MMM yyyy, HH:mm")
                price.text = getString(R.string.price, trip.price.toString())
                seats.text = "${trip.seatAvailable}/${trip.seats}"
                bags.text = getString(trip.bags.textInt)

                destinationPickUp.text = trip.destPickUp.fullTitle

                car.text = trip.car.baseInfo

                pictureFrom.loadImageFromUrl(trip.destFrom.administrative?.image)
                pictureTo.loadImageFromUrl(trip.destTo.administrative?.image)

                if (it.first.creatorUser.id == it.second.id) {
                    arrow.backgroundTintList = resources.getColorStateList(R.color.green_20_new, null)
                    arrow.imageTintList = resources.getColorStateList(R.color.green_new, null)
                    pickUpImage.loadDirectionsImage(trip, getString(R.color.green_new))
                } else {
                    arrow.backgroundTintList = resources.getColorStateList(R.color.orange_20_new, null)
                    arrow.imageTintList = resources.getColorStateList(R.color.orange_new, null)
                    pickUpImage.loadDirectionsImage(trip, getString(R.color.orange_new))
                }

                if (trip.msg.isNullOrEmpty()) {
                    description.text = getString(R.string.no_description_available)
                } else {
                    description.text = trip.msg
                }

                destinationPickUp.onClickListener {
                    openGoogleMaps(trip.destPickUp)
                }

                openInMaps.setOnClickListener {
                    openGoogleMaps(trip)
                }
            }
            binding.genericLoader.root.visibility = View.GONE
        })
    }

    override fun setUpViews() {

        binding.back.button.setOnClickListener {
            finish()
        }
        binding.genericLoader.root.visibility = View.VISIBLE

        if (currentTrip == null) {

        } else {
            viewModel.setTrip(currentTrip!!)
        }
    }
}
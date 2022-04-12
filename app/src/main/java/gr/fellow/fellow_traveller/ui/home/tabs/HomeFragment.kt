package gr.fellow.fellow_traveller.ui.home.tabs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentHomeBinding
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.flyToPoint
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResult
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.extensions.then
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripActivity
import gr.fellow.fellow_traveller.ui.views.TripRad
import gr.fellow.fellow_traveller.ui.views.TripRadioOnClickListener
import gr.fellow.fellow_traveller.utils.CITY


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    private val viewModel: HomeViewModel by activityViewModels()
    private var destinations = Pair<Destination?, Destination?>(null, null)
    private var type = TripRad.OFFER

    override fun getViewBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)


    @SuppressLint("SetTextI18n")
    override fun setUpObservers() {
        /*  viewModel.user.observe(viewLifecycleOwner, {
              //Delete the "ς" or "s" when we display the name
              if (it.firstName.takeLast(1) == "ς")
                  binding.userName.text = it.firstName.dropLast(1) + ","
              else
                  binding.userName.text = it.firstName + ","

          })*/
    }

    override fun setUpViews() {
        /*binding.mapView.apply {
            logo.enabled = false
            scalebar.enabled = false
            attribution.enabled = false
            compass.enabled = false

            gestures.updateSettings {
                scrollEnabled = true
                pinchToZoomEnabled = true
                rotateEnabled = false
            }
        }
*/
        binding.radio.onClick = object : TripRadioOnClickListener {
            override fun onClick(type: TripRad) {
                updateColor(type)
            }
        }


        binding.submit.setOnClickListener {
            val (from, to) = destinations
            clearDestinations()
            if (type == TripRad.OFFER) {
                startActivityForResult(
                    NewTripActivity::class, 2, bundleOf(
                        "destinationFrom" to from,
                        "destinationTo" to to,
                        "localUser" to viewModel.user.value!!
                    )
                )
            } else {
                /*startActivityForResult(
                    SearchTripActivity::class, 2, bundleOf(
                        "userRate" to viewModel.user.value?.rate,
                        "userRate" to viewModel.user.value?.rate,
                        "localUser" to viewModel.user.value!!
                    )
                )*/
            }

        }

        binding.textViewFrom.setOnClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 3, bundleOf("autocompleteType" to CITY))
        }

        binding.destinationFrom.setOnClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 3, bundleOf("autocompleteType" to CITY))
        }

        binding.textViewTo.setOnClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 4, bundleOf("autocompleteType" to CITY))
        }

        binding.destinationTo.setOnClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 4, bundleOf("autocompleteType" to CITY))
        }
    }

    private fun updateColor(typeTemp: TripRad) {
        type = typeTemp
        val backgroundTint = (type == TripRad.SEARCH) then { resources.getColorStateList(R.color.orange_20_new, null) } ?: resources.getColorStateList(R.color.green_60_new, null)
        val color = (type == TripRad.SEARCH) then { ContextCompat.getColor(this.requireContext(), R.color.orange_new) } ?: ContextCompat.getColor(this.requireContext(), R.color.green_new)

        binding.imageViewFrom.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        binding.imageViewFrom.backgroundTintList = backgroundTint

        binding.imageViewTo.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        binding.imageViewTo.backgroundTintList = backgroundTint

        binding.submit.backgroundTintList = (type == TripRad.SEARCH) then { resources.getColorStateList(R.color.orange_new, null) } ?: resources.getColorStateList(R.color.green_new, null)

        binding.submit.text = (type == TripRad.OFFER) then { resources.getString(R.string.offer_trip) } ?: resources.getString(R.string.search_trip)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                    //viewModel.addTripCreate(it)
                }
            }

        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val trip = data?.getParcelableExtra<TripInvolved>("trip")
                trip?.let {
                    // viewModel.addTripPassenger(it)
                }
            }

        }


        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                val destination = data?.getParcelableExtra<Destination>("place")!!
                destinations = Pair(destination, destinations.second)

                setDestinationFrom()
                handelSubmitButton()
            }

        }


        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                val destination = data?.getParcelableExtra<Destination>("place")!!
                destinations = Pair(destinations.first, destination)

                setDestinationTo()
                handelSubmitButton()
            }

        }
    }

    private fun clearDestinations() {
        destinations = Pair(null, null)
        binding.destinationFrom.text = null
        binding.destinationTo.text = null
        handelSubmitButton()
    }

    private fun handelSubmitButton() {
        val (destinationFrom, destinationTo) = destinations

        if (destinationFrom != null && destinationTo != null) {
            binding.submit.visibility = View.VISIBLE
        } else {
            binding.submit.visibility = View.GONE
        }

    }

    private fun setDestinationFrom() {
        val (destinationFrom, _) = destinations

        binding.destinationFrom.setText(destinationFrom?.title ?: "")


        if (destinationFrom != null) {
          //  binding.mapView.addMarker(destinationFrom, type = type)
          //  binding.mapView.flyToPoint(destinationFrom)
        }
    }


    private fun setDestinationTo() {
        val (_, destinationTo) = destinations

        binding.destinationTo.setText(destinationTo?.title ?: "")


        if (destinationTo != null) {
         //   binding.mapView.addMarker(destinationTo, true, type)
         //   binding.mapView.flyToPoint(destinationTo)
        }
    }

}


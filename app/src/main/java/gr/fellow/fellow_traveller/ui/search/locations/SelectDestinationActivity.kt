package gr.fellow.fellow_traveller.ui.search.locations

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySelectDestinationBinding
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.location.SelectLocationViewModel
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter

@AndroidEntryPoint
class SelectDestinationActivity : BaseActivity<ActivitySelectDestinationBinding>(), View.OnClickListener {

    private val viewModel: SelectLocationViewModel by viewModels()
    private var placesList = mutableListOf<PredictionResponse>()

    override fun provideViewBinding(): ActivitySelectDestinationBinding =
        ActivitySelectDestinationBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.destinations.observe(this, Observer {
            if (it.isNotEmpty()) {
                placesList.clear()
                placesList.addAll(it)
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

        })

        viewModel.place.observe(this, Observer {
            sendPlaceToParent(it)
        })

    }

    override fun setUpViews() {

        if ("to" == intent.getStringExtra("info"))
            binding.endDestLabel.text = "Επιλέξτε τον προορισμό σας"
        else
            binding.endDestLabel.text = "Επιλέξτε την αφετηρία σας"

        with(binding) {
            athensButton.setOnClickListener(this@SelectDestinationActivity)
            thessalonikiButton.setOnClickListener(this@SelectDestinationActivity)
            ioanninaButton.setOnClickListener(this@SelectDestinationActivity)
            patraButton.setOnClickListener(this@SelectDestinationActivity)
            larisaButton.setOnClickListener(this@SelectDestinationActivity)
            backButton.setOnClickListener {
                onBackPressed()
            }
        }


        binding.destEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().length > 2) {
                    binding.protaseis.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    viewModel.getAllDestinations(charSequence.toString().trim())
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.protaseis.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        initializeRecycle()
    }


    private fun initializeRecycle() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@SelectDestinationActivity)
            recyclerView.adapter = PlacesAdapter(placesList, this@SelectDestinationActivity::onPredictionItemSelected)
        }
    }


    private fun onPredictionItemSelected(predictionResponse: PredictionResponse) {
        viewModel.getLanLogForPlace(predictionResponse.placeId, predictionResponse.description)
    }


    private fun sendPlaceToParent(place: PlaceModel) {
        val resultIntent = Intent()
        resultIntent.putExtra("place", place)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


    override fun onClick(view: View) {
        lateinit var place: PlaceModel

        when (view.id) {
            binding.athensButton.id -> {
                place = PlaceModel("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", "Αθήνα", 37.97534.toFloat(), 23.736151.toFloat())
            }
            binding.thessalonikiButton.id -> {
                place = PlaceModel("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", "Θεσσαλονίκη", 40.634781.toFloat(), 22.943090.toFloat())
            }
            binding.ioanninaButton.id -> {
                place = PlaceModel("ChIJZ93-3qLpWxMRwJe54iy9AAQ", "Ιωάννινα", 39.674530.toFloat(), 20.840210.toFloat())
            }
            binding.patraButton.id -> {
                place = PlaceModel("ChIJLe0kpZk1XhMRoIy54iy9AAQ", "Πάτρα", 38.246639.toFloat(), 21.734573.toFloat())
            }
            binding.larisaButton.id -> {
                place = PlaceModel("ChIJoUddWVyIWBMRMJy54iy9AAQ", "Λάρισα", 39.638779.toFloat(), 22.415979.toFloat())
            }
        }
        sendPlaceToParent(place)
    }


}
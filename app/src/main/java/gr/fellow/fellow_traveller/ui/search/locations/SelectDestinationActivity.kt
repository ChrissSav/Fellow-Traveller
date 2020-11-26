package gr.fellow.fellow_traveller.ui.search.locations

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySelectDestinationBinding
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.location.SelectLocationViewModel
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesPopularAdapter

@AndroidEntryPoint
class SelectDestinationActivity : BaseActivity<ActivitySelectDestinationBinding>() {

    private val viewModel: SelectLocationViewModel by viewModels()
    private var placesList = mutableListOf<PredictionResponse>()
    private var placesListPopular = mutableListOf<PredictionResponse>()

    override fun provideViewBinding(): ActivitySelectDestinationBinding =
        ActivitySelectDestinationBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.destinations.observe(this, Observer {
            placesList.clear()
            placesList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.recyclerViewPopular.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        })

        viewModel.place.observe(this, Observer {
            sendPlaceToParent(it)
        })

    }

    override fun setUpViews() {
        placesListPopular = mutableListOf(
            PredictionResponse("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.city_athens)),
            PredictionResponse("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.city_thessaloniki)),
            PredictionResponse("ChIJZ93-3qLpWxMRwJe54iy9AAQ", getString(R.string.city_ioannina)),
            PredictionResponse("ChIJLe0kpZk1XhMRoIy54iy9AAQ", getString(R.string.city_patra)),
            PredictionResponse("ChIJoUddWVyIWBMRMJy54iy9AAQ", getString(R.string.city_larisa)),
            PredictionResponse("ChIJfdnlaDstrBQR3tAiUqN47vY", getString(R.string.city_ksanthi)),
            PredictionResponse("ChIJ1eUpZ4txqRQRV-NcWXB83Qk", getString(R.string.city_serres)),
            PredictionResponse("ChIJ1cgCXJ8cshQR40EeRCv5sLo", getString(R.string.city_aleksandroupoli))
        )

        /*if ("to" == intent.getStringExtra("info"))
            binding.endDestLabel.text = getString(R.string.enter_destination)
        else
            binding.endDestLabel.text = getString(R.string.enter_starting_point)*/


        binding.backButton.setOnClickListener {
            onBackPressed()
        }



        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().length > 2)
                    viewModel.getAllDestinations(charSequence.toString().trim())
                else if (charSequence.toString().trim().isEmpty()) {
                    binding.recyclerViewPopular.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
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
            recyclerView.adapter = PlacesAdapter(placesList, this@SelectDestinationActivity::onPredictionItemSelected)
            recyclerViewPopular.adapter = PlacesPopularAdapter(placesListPopular, this@SelectDestinationActivity::onPredictionItemSelected)
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


}
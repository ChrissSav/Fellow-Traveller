package gr.fellow.fellow_traveller.ui.location

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
import gr.fellow.fellow_traveller.databinding.ActivitySelectLocationBinding
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesPopularAdapter

@AndroidEntryPoint
class SelectLocationActivity : BaseActivity<ActivitySelectLocationBinding>() {

    private val viewModel: SelectLocationViewModel by viewModels()
    private var placesList = mutableListOf<PredictionResponse>()
    private var placesListPopular = mutableListOf<PredictionResponse>()


    override fun provideViewBinding(): ActivitySelectLocationBinding =
        ActivitySelectLocationBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.destinations.observe(this, Observer {
            placesList.clear()
            placesList.addAll(it)
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.recyclerViewPopular.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
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


        with(binding) {
            recyclerView.adapter = PlacesAdapter(placesList, this@SelectLocationActivity::onPredictionItemSelected)
            recyclerViewPopular.adapter = PlacesPopularAdapter(placesListPopular, this@SelectLocationActivity::onPredictionItemSelected)

            backButton.setOnClickListener {
                finish()
            }

            selectLocationActivityEditTextSearchPlace.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(charSequence: Editable) {
                    if (charSequence.toString().trim().length > 2)
                        viewModel.getAllDestinations(charSequence.toString().trim())
                    else if (charSequence.toString().trim().isEmpty()) {
                        recyclerViewPopular.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }
            })
        }
    }

    private fun onPredictionItemSelected(predictionResponse: PredictionResponse) {
        val resultIntent = Intent()
        resultIntent.putExtra("id", predictionResponse.placeId)
        resultIntent.putExtra("title", predictionResponse.description)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    override fun finish() {
        super.finish()
        hideKeyboard()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


}
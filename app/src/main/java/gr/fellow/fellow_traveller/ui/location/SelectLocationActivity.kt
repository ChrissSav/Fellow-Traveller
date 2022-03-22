package gr.fellow.fellow_traveller.ui.location

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivitySelectLocationBinding
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.location.adapter.DestinationAdapter
import gr.fellow.fellow_traveller.utils.ADDRESS
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class SelectLocationActivity : BaseActivityViewModel<ActivitySelectLocationBinding, SelectLocationViewModel>(SelectLocationViewModel::class.java) {

    private var placesList = mutableListOf<Destination>()
    private var placesListPopular = mutableListOf<Destination>()
    private var isLocked = false
    private var autocompleteType = ADDRESS

    override fun provideViewBinding(): ActivitySelectLocationBinding =
        ActivitySelectLocationBinding.inflate(layoutInflater)


    override fun handleIntent() {
        autocompleteType = intent.getStringExtra("autocompleteType") ?: ADDRESS
    }

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

        if (autocompleteType == ADDRESS)
            binding.constraintLayoutPopular.visibility = View.GONE

        placesListPopular = mutableListOf(
            Destination("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic", getString(R.string.placeholder_city_athens), null, null, null),
            Destination("ChIJ7eAoFPQ4qBQRqXTVuBXnugk", getString(R.string.placeholder_city_thessaloniki), null, null, null),
            Destination("ChIJZ93-3qLpWxMRwJe54iy9AAQ", getString(R.string.placeholder_city_ioannina), null, null, null),
            Destination("ChIJLe0kpZk1XhMRoIy54iy9AAQ", getString(R.string.placeholder_city_patra), null, null, null),
            Destination("ChIJoUddWVyIWBMRMJy54iy9AAQ", getString(R.string.placeholder_city_larisa), null, null, null),
            Destination("ChIJfdnlaDstrBQR3tAiUqN47vY", getString(R.string.placeholder_city_ksanthi), null, null, null),
            Destination("ChIJ1eUpZ4txqRQRV-NcWXB83Qk", getString(R.string.placeholder_city_serres), null, null, null),
            Destination("ChIJ1cgCXJ8cshQR40EeRCv5sLo", getString(R.string.placeholder_city_aleksandroupoli), null, null, null)
        )


        with(binding) {
            recyclerView.adapter = DestinationAdapter(placesList, this@SelectLocationActivity::onPredictionItemSelected)
            recyclerViewPopular.adapter = DestinationAdapter(placesListPopular, this@SelectLocationActivity::onPredictionItemSelected)

            backButton.setOnClickListener {
                finish()
            }

            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(charSequence: Editable) {
                    autocompleteLock {
                        if (charSequence.toString().trim().length > 2)
                            viewModel.getAutocomplete(charSequence.toString().trim(), autocompleteType)
                        else if (charSequence.toString().trim().isEmpty()) {
                            recyclerViewPopular.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }
            })
        }
    }

    private fun autocompleteLock(function: () -> Unit) {
        if (!isLocked) {
            isLocked = true
            function.invoke()
            Timer().schedule(750) {
                isLocked = false
            }
        }
    }

    private fun onPredictionItemSelected(predictionResponse: Destination) {
        val resultIntent = Intent()
        resultIntent.putExtra("id", predictionResponse.placeId)
        resultIntent.putExtra("title", predictionResponse.title)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    override fun finish() {
        super.finish()
        hideKeyboard()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


}
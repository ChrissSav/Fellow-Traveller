package gr.fellow.fellow_traveller.ui.location

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySelectLocationBinding
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter

@AndroidEntryPoint
class SelectLocationActivity : BaseActivity<ActivitySelectLocationBinding>() {

    private val viewModel: SelectLocationViewModel by viewModels()
    private var placesList = mutableListOf<PredictionResponse>()


    override fun provideViewBinding(): ActivitySelectLocationBinding =
        ActivitySelectLocationBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.destinations.observe(this, Observer {
            placesList.clear()
            placesList.addAll(it)
            binding.RecyclerView.adapter?.notifyDataSetChanged()
        })

    }

    override fun setUpViews() {
        with(binding) {
            RecyclerView.adapter = PlacesAdapter(placesList, this@SelectLocationActivity::onPredictionItemSelected)

            backButton.setOnClickListener {
                finish()
            }

            selectLocationActivityEditTextSearchPlace.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(charSequence: Editable) {
                    if (charSequence.toString().trim().length > 2)
                    //Log.i("addTextChangedListener", "afterTextChanged "+s);
                        viewModel.getAllDestinations(charSequence.toString().trim())
                    //  GetPlaces(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    //Log.i("addTextChangedListener", "beforeTextChanged");
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    //Log.i("addTextChangedListener", "onTextChanged");
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
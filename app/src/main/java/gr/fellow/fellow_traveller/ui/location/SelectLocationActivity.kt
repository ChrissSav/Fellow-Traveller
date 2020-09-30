package gr.fellow.fellow_traveller.ui.location

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySelectLocationBinding
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.hideKeyboard
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter

@AndroidEntryPoint
class SelectLocationActivity : BaseActivity<ActivitySelectLocationBinding>() {

    private val viewModel: SelectLocationViewModel by viewModels()
    private var placesList = mutableListOf<PredictionResponse>()


    override fun provideViewBinding(): ActivitySelectLocationBinding =
        ActivitySelectLocationBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.destinations.observe(this, Observer {
            showHideInternetMessage(0)
            placesList.clear()
            placesList.addAll(it)
            binding.RecyclerView.adapter?.notifyDataSetChanged()
        })

        viewModel.error.observe(this, Observer {
            if (it == R.string.ERROR_INTERNET_CONNECTION) {
                showHideInternetMessage(100)
            } else {
                createAlerter(getString(it))
            }

        })
    }

    override fun setUpViews() {
        with(binding) {
            RecyclerView.adapter = PlacesAdapter(placesList) {
                val resultIntent = Intent()
                resultIntent.putExtra("id", it.placeId)
                resultIntent.putExtra("title", it.description)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

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


    override fun finish() {
        super.finish()
        hideKeyboard()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun showHideInternetMessage(targetHeight: Int) {
        val slideAnimator = ValueAnimator
            .ofInt(binding.imageViewInternet.layoutParams.height, targetHeight)
            .setDuration(200)
        slideAnimator.addUpdateListener { animation1: ValueAnimator ->
            val value = animation1.animatedValue as Int
            binding.imageViewInternet.layoutParams.height = value
            binding.imageViewInternet.requestLayout()
        }
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.start()
    }


}
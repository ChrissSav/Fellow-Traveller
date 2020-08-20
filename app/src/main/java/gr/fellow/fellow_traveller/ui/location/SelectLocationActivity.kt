package gr.fellow.fellow_traveller.ui.location

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivitySelectLocationBinding
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.location.adapter.PlacesAdapter

@AndroidEntryPoint
class SelectLocationActivity : AppCompatActivity() {


    private val selectLocationViewModel: SelectLocationViewModel by viewModels()

    private lateinit var binding: ActivitySelectLocationBinding
    private lateinit var mAdapter: PlacesAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var placesList = mutableListOf<PredictionResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeRecycle()

        selectLocationViewModel.destinations.observe(this, Observer {
            showHideBottomNav(0)
            placesList.clear()
            placesList.addAll(it)
            binding.RecyclerView.adapter?.notifyDataSetChanged()
        })

        selectLocationViewModel.error.observe(this, Observer {
            if (it == R.string.ERROR_INTERNET_CONNECTION) {
                showHideBottomNav(100)
            } else {
                createAlerter(getString(it))
            }


        })

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.SelectLocationActivityEditTextSearchPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //Log.i("addTextChangedListener", "afterTextChanged "+s);
                selectLocationViewModel.getAllDestinations(s.toString())
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

    private fun showHideBottomNav(targetHeight: Int) {
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


    private fun initializeRecycle() {
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = PlacesAdapter(placesList) {
            val resultIntent = Intent()
            resultIntent.putExtra("id", it.placeId)
            resultIntent.putExtra("title", it.description)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        with(binding) {
            RecyclerView.layoutManager = mLayoutManager
            RecyclerView.adapter = mAdapter
        }


    }

}
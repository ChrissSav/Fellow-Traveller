package gr.fellow.fellow_traveller.ui.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivitySelectLocationBinding
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.createToast
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
            if (it.isNotEmpty()) {
                placesList.clear()
                placesList.addAll(it)
                binding.RecyclerView.adapter?.notifyDataSetChanged()
            }

        })
        selectLocationViewModel.error.observe(this, Observer {
            createToast(this, it)
        })

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.SelectLocationActivityEditTextSearchPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //Log.i("addTextChangedListener", "afterTextChanged "+s);
                if (s.toString().length > 2)
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
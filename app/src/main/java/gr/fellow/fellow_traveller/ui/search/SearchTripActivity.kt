package gr.fellow.fellow_traveller.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding
import gr.fellow.fellow_traveller.ui.createAlerter


@AndroidEntryPoint
class SearchTripActivity : AppCompatActivity() {

    private val searchViewModel: SearchTripViewModel by viewModels()

    private lateinit var binding: ActivitySearchTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })

    }
}
package gr.fellow.fellow_traveller.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchTripActivity : AppCompatActivity() {

    private val searchViewModel: SearchTripViewModel by viewModels()

    private lateinit var binding: ActivitySearchTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchTripBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
package gr.fellow.fellow_traveller.ui.search

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchTripBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)    }
}
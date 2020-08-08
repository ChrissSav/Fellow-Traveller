package gr.fellow.fellow_traveller.ui.search.locations

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import gr.fellow.fellow_traveller.databinding.ActivitySelectDestinationBinding
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel

class SelectDestinationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySelectDestinationBinding
    private lateinit var destinationModelToReturn: PlaceModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySelectDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            athensButton.setOnClickListener { this@SelectDestinationActivity }
            thessalonikiButton.setOnClickListener { this@SelectDestinationActivity }
            ioanninaButton.setOnClickListener { this@SelectDestinationActivity }
            patraButton.setOnClickListener { this@SelectDestinationActivity }
            larisaButton.setOnClickListener { this@SelectDestinationActivity }
        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.athensButton.id -> {

            }
            binding.thessalonikiButton.id -> {

            }
            binding.ioanninaButton.id -> {

            }
            binding.patraButton.id -> {

            }
            binding.larisaButton.id -> {

            }

        }
    }


}
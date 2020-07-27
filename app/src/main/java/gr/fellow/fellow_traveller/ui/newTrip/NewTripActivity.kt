package gr.fellow.fellow_traveller.ui.newTrip

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel

class NewTripActivity : AppCompatActivity() {


    private lateinit var  binding: ActivityNewTripBinding
    private val newTripViewModel: NewTripViewModel by viewModels()
    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityNewTripBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        nav.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.phoneFragment -> binding.progressBar.progress = 25
                R.id.emailFragment -> binding.progressBar.progress = 50
                R.id.passwordFragment -> binding.progressBar.progress = 75
                R.id.accountFragment -> binding.progressBar.progress = 90
            }

        })


        newTripViewModel.error.observe(this, Observer {
            createSnackBar(view, it)
        })

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }
}
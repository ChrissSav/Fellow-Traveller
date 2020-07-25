package gr.fellow.fellow_traveller.ui.newTrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityNewBinding
import gr.fellow.fellow_traveller.databinding.ActivityRegisterBinding
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewActivity : AppCompatActivity() {


    private lateinit var  binding: ActivityNewBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var nav: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
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


        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }
}
package gr.fellow.fellow_traveller.ui.register

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityRegisterBinding
import gr.fellow.fellow_traveller.ui.createAlerter


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var nav: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        registerViewModel.load.observe(this, Observer {
            if(it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE

        })


        registerViewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })

        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }
}
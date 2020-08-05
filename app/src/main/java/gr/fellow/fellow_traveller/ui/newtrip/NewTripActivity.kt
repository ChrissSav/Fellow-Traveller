package gr.fellow.fellow_traveller.ui.newtrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewTripActivity : AppCompatActivity(), ExitCustomDialog.ExitCustomDialogListener {


    private lateinit var binding: ActivityNewTripBinding
    private val newTripViewModel: NewTripViewModel by viewModels()
    private lateinit var nav: NavController
    private lateinit var exitCustomDialog: ExitCustomDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityNewTripBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        nav.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.destinationsFragment -> binding.progressBar.progress = 14
                R.id.pickUpFragment -> binding.progressBar.progress = 28
                R.id.dateTimeFragment -> binding.progressBar.progress = 42
                R.id.baseInfoFragment -> binding.progressBar.progress = 56
                R.id.priceFragment -> binding.progressBar.progress = 70
                R.id.messageFragment -> {
                    binding.progressBar.progress = 84
                    binding.labelSummary.visibility = View.INVISIBLE
                }
                R.id.summaryFragment -> {
                    binding.progressBar.progress = 95
                    binding.labelSummary.visibility = View.VISIBLE
                }
                R.id.successTripFragment -> {
                    binding.constraintLayout.visibility = View.GONE
                }

            }

        })
        newTripViewModel.setBags(0)
        newTripViewModel.setSeats(1)
        newTripViewModel.error.observe(this, Observer {
            createSnackBar(view, it)
        })
        binding.imageButtonExit.setOnClickListener {
            openDialog()
        }

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun openDialog() {
        exitCustomDialog = ExitCustomDialog(this)
        exitCustomDialog.show(supportFragmentManager, "example dialog")
    }

    override fun exitFrom(exit: Boolean) {
        exitCustomDialog.dismiss()
        if (exit)
            finish()
    }
}
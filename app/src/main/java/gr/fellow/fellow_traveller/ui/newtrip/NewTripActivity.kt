package gr.fellow.fellow_traveller.ui.newtrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog


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
                R.id.destinationsFragment -> {
                    binding.progressBar.progress = 14
                    binding.labelSummary.visibility = View.INVISIBLE

                }
                R.id.pickUpFragment -> {
                    binding.progressBar.progress = 28
                    binding.labelSummary.visibility = View.INVISIBLE

                }
                R.id.dateTimeFragment -> {
                    binding.progressBar.progress = 42
                    binding.labelSummary.visibility = View.INVISIBLE

                }
                R.id.baseInfoFragment -> {
                    binding.progressBar.progress = 56
                    binding.labelSummary.visibility = View.INVISIBLE

                }
                R.id.priceFragment -> {
                    binding.progressBar.progress = 70
                    binding.labelSummary.visibility = View.INVISIBLE
                }
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
        newTripViewModel.setPet(false)
        newTripViewModel.setSeats(1)

        newTripViewModel.error.observe(this, Observer {
            createAlerter(getString(it), R.color.blue_color)
        })

        newTripViewModel.load.observe(this, Observer {
            if (it)
                binding.progressLoad.visibility = View.VISIBLE
            else
                binding.progressLoad.visibility = View.GONE

        })

        newTripViewModel.finish.observe(this, Observer {
            val trip = newTripViewModel.success.value
            trip?.let {
                val resultIntent = Intent()
                resultIntent.putExtra("trip", it)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        })
        binding.imageButtonExit.setOnClickListener {
            openDialog()
        }

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onBackPressed() {
        if (nav.currentDestination?.id != R.id.successTripFragment) {
            super.onBackPressed()
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
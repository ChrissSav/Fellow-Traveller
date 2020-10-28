package gr.fellow.fellow_traveller.ui.newtrip

import android.annotation.SuppressLint
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard


@AndroidEntryPoint
class NewTripActivity : BaseActivity<ActivityNewTripBinding>(), ExitCustomDialog.ExitCustomDialogListener {


    private val viewModel: NewTripViewModel by viewModels()
    private lateinit var nav: NavController
    private lateinit var exitCustomDialog: ExitCustomDialog

    override fun provideViewBinding(): ActivityNewTripBinding =
        ActivityNewTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.setSeats(1)

        viewModel.errorSecond.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId), R.color.blue_color)
            else
                createAlerter(it.message, R.color.blue_color)
        })

        viewModel.load.observe(this, Observer {
            if (it)
                binding.progressLoad.visibility = View.VISIBLE
            else
                binding.progressLoad.visibility = View.GONE

        })


    }

    override fun setUpViews() {
        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        nav.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            hideKeyboard()
            when (destination.id) {
                R.id.destinationsFragment -> binding.progressBar.progress = 30
                R.id.dateTimeFragment -> binding.progressBar.progress = 42
                R.id.baseInfoFragment -> binding.progressBar.progress = 56
                R.id.priceFragment -> binding.progressBar.progress = 70
                R.id.messageFragment -> binding.progressBar.progress = 84
                R.id.summaryFragment -> binding.progressBar.progress = 95
                R.id.successTripFragment -> binding.constraintLayout.visibility = View.GONE
            }

        })


        binding.imageButtonExit.setOnClickListener {
            openDialog()
        }

        binding.imageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }


    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {

        if (nav.backStack.size == 2) {
            openDialog()
        } else if (nav.currentDestination?.id != R.id.successTripFragment) {
            super.onBackPressed()
        }


    }

    private fun openDialog() {
        exitCustomDialog = ExitCustomDialog(this)
        exitCustomDialog.show(supportFragmentManager, "example dialog")
    }

    override fun exitFrom(result: Boolean) {
        exitCustomDialog.dismiss()
        if (result)
            finish()
    }


}
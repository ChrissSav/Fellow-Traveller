package gr.fellow.fellow_traveller.ui.newtrip

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityNewTripBinding
import gr.fellow.fellow_traveller.domain.AnswerType
import gr.fellow.fellow_traveller.ui.dialogs.ExitCustomDialog
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard


@AndroidEntryPoint
class NewTripActivity : BaseActivityViewModel<ActivityNewTripBinding, NewTripViewModel>(NewTripViewModel::class.java) {


    private lateinit var nav: NavController
    private var userRate: Float = 0f

    override fun provideViewBinding(): ActivityNewTripBinding =
        ActivityNewTripBinding.inflate(layoutInflater)


    override fun handleIntent() {
        userRate = intent.getFloatExtra("userRate", 0f)
    }

    override fun setUpObservers() {

        viewModel.carList.observe(this, Observer { list ->
            if (list.size == 1)
                viewModel.setCar(list.first())
        })

        viewModel.error.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId), R.color.green)
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

        viewModel.loadUserCars()
        viewModel.setSeats(1)


        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        nav.addOnDestinationChangedListener { _, destination, _ ->
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

        }


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


    fun getUserRate() = userRate

    private fun openDialog() {
        ExitCustomDialog(this, this::exitCustomDialogAnswerType, getString(R.string.prompt_cancel_offer), 2).show(supportFragmentManager, "exitCustomDialog")
    }

    private fun exitCustomDialogAnswerType(result: AnswerType) {
        if (result == AnswerType.Yes)
            finish()
    }


}
package gr.fellow.fellow_traveller.ui.register

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityRegisterBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.hideKeyboard


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {


    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var nav: NavController

    override fun provideViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE

        })

        viewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })

        viewModel.errorSecond.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else
                createAlerter(it.message)

        })
    }

    override fun setUpViews() {
        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        nav.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            hideKeyboard()
            when (destination.id) {
                R.id.emailFragment -> binding.progressBar.progress = 25
                R.id.passwordFragment -> binding.progressBar.progress = 50
                R.id.accountFragment -> binding.progressBar.progress = 75
                R.id.successRegistrationFragment -> binding.progressBar.progress = 100
            }

        })



        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }


}
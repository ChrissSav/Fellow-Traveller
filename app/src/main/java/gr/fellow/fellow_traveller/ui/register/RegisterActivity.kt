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
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.initializeBlur


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {


    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var nav: NavController

    override fun provideViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.root.visibility = View.VISIBLE
            else
                binding.genericLoader.root.visibility = View.INVISIBLE

        })


        viewModel.error.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else
                createAlerter(it.message)

        })
    }

    override fun setUpViews() {

        initializeBlur(binding.genericLoader.blurView)


        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)

        nav.addOnDestinationChangedListener { _, destination, _ ->
            hideKeyboard()
            when (destination.id) {
                R.id.emailFragment -> binding.progressBar.progress = 30
                R.id.passwordFragment -> binding.progressBar.progress = 60
                R.id.accountFragment -> binding.progressBar.progress = 80
                R.id.successRegistrationFragment -> {
                    binding.progressBar.progress = 100
                    binding.ImageButtonBack.visibility = View.GONE
                }
            }

        }



        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }


}
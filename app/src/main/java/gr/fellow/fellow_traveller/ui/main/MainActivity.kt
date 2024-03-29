package gr.fellow.fellow_traveller.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityMainBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private var goToLogin = false
    private lateinit var nav: NavController

    override fun handleIntent() {
        goToLogin = intent.getBooleanExtra("login", false)
    }

    override fun provideViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        with(viewModel) {

            load.observe(this@MainActivity, Observer {
                if (it)
                    binding.genericLoader.root.visibility = View.VISIBLE
                else
                    binding.genericLoader.root.visibility = View.INVISIBLE
            })


            error.observe(this@MainActivity, Observer {
                if (it.internal)
                    createAlerter(getString(it.messageId))
                else
                    createAlerter(it.message)
            })
        }
    }

    override fun setUpViews() {


        nav = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        if (goToLogin)
            nav.navigate(R.id.action_mainFragment_to_loginFragment)
    }


}
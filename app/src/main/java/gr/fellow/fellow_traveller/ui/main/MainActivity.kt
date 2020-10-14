package gr.fellow.fellow_traveller.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityMainBinding
import gr.fellow.fellow_traveller.ui.createAlerter


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun provideViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        with(viewModel) {

            load.observe(this@MainActivity, Observer {
                if (it)
                    binding.genericLoader.progressLoad.visibility = View.VISIBLE
                else
                    binding.genericLoader.progressLoad.visibility = View.INVISIBLE
            })


            error.observe(this@MainActivity, Observer {
                createAlerter(getString(it))
            })
        }
    }

    override fun setUpViews() {}




}
package gr.fellow.fellow_traveller.ui.search

import android.view.View
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding


@AndroidEntryPoint
class SearchTripActivity : BaseActivityViewModel<ActivitySearchTripBinding, SearchTripViewModel>(SearchTripViewModel::class.java) {


    override fun provideViewBinding(): ActivitySearchTripBinding =
        ActivitySearchTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE
        })
    }


    override fun setUpViews() {}
}
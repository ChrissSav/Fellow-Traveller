package gr.fellow.fellow_traveller.ui.search

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding
import gr.fellow.fellow_traveller.ui.createAlerter


@AndroidEntryPoint
class SearchTripActivity : BaseActivity<ActivitySearchTripBinding>() {

    private val searchViewModel: SearchTripViewModel by viewModels()

    override fun provideViewBinding(): ActivitySearchTripBinding =
        ActivitySearchTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        searchViewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })


        searchViewModel.finish.observe(this, Observer {
            val trip = searchViewModel.tripBook.value
            trip?.let {
                val resultIntent = Intent()
                resultIntent.putExtra("trip", it)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        })
    }

    override fun setUpViews() {}
}
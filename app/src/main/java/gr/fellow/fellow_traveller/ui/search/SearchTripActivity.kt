package gr.fellow.fellow_traveller.ui.search

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySearchTripBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter


@AndroidEntryPoint
class SearchTripActivity : BaseActivity<ActivitySearchTripBinding>() {

    private val viewModel: SearchTripViewModel by viewModels()

    override fun provideViewBinding(): ActivitySearchTripBinding =
        ActivitySearchTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })


        viewModel.finish.observe(this, Observer {
            val trip = viewModel.tripBook.value
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
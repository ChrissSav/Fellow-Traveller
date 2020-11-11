package gr.fellow.fellow_traveller.ui.search.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessTripBookBinding
import gr.fellow.fellow_traveller.ui.extensions.postDelay
import gr.fellow.fellow_traveller.ui.extensions.startAnimation
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel

@AndroidEntryPoint
class SuccessTripBookFragment : BaseFragment<FragmentSuccessTripBookBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentSuccessTripBookBinding =
        FragmentSuccessTripBookBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {
        binding.view3.startAnimation()

        postDelay(1200) {
            val trip = viewModel.tripBook.value
            trip?.let {
                val resultIntent = Intent()
                resultIntent.putExtra("trip", it)
                activity?.setResult(Activity.RESULT_OK, resultIntent)
                activity?.finish()
            }

        }
    }

}
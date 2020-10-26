package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessTripBinding
import gr.fellow.fellow_traveller.ui.extensions.postDelay
import gr.fellow.fellow_traveller.ui.extensions.startAnimation
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


@AndroidEntryPoint
class SuccessTripFragment : BaseFragment<FragmentSuccessTripBinding>() {
    private val viewModel: NewTripViewModel by activityViewModels()

    override fun getViewBinding(): FragmentSuccessTripBinding =
        FragmentSuccessTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {

        postDelay(350) {
            binding.view3.startAnimation()
            val trip = viewModel.success.value
            trip?.let {
                val resultIntent = Intent()
                resultIntent.putExtra("trip", it)
                activity?.setResult(Activity.RESULT_OK, resultIntent)
                activity?.finish()
            }
        }
    }


}
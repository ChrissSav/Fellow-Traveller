package gr.fellow.fellow_traveller.ui.search.fragments

import android.animation.Animator
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessTripBookBinding
import gr.fellow.fellow_traveller.ui.postDelay
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel

@AndroidEntryPoint
class SuccessTripBookFragment : BaseFragment<FragmentSuccessTripBookBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentSuccessTripBookBinding =
        FragmentSuccessTripBookBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {
        postDelay(500) {
            binding.textViewHover.animate()
                .translationX(binding.textViewHover.width.toFloat())
                .alpha(1.0f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        viewModel.finish()

                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
        }
    }

}
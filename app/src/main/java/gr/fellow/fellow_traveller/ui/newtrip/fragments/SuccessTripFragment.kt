package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessTripBinding
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.postDelay


@AndroidEntryPoint
class SuccessTripFragment : BaseFragment<FragmentSuccessTripBinding>() {
    private val viewModel: NewTripViewModel by activityViewModels()

    override fun getViewBinding(): FragmentSuccessTripBinding =
        FragmentSuccessTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {
        postDelay(500) {
            binding.SuccessActivityTextViewHover.animate()
                .translationX(binding.SuccessActivityTextViewHover.width.toFloat())
                .alpha(1.0f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        val trip = viewModel.success.value
                        trip?.let {
                            val resultIntent = Intent()
                            resultIntent.putExtra("trip", it)
                            activity?.setResult(Activity.RESULT_OK, resultIntent)
                            activity?.finish()
                        }

                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
        }
    }


}
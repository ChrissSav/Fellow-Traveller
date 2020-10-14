package gr.fellow.fellow_traveller.ui.register.fragments


import android.os.Handler
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessRegistrationBinding

@AndroidEntryPoint
class SuccessRegistrationFragment : BaseFragment<FragmentSuccessRegistrationBinding>() {


    override fun getViewBinding(): FragmentSuccessRegistrationBinding =
        FragmentSuccessRegistrationBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {
        Handler().postDelayed({
            binding.textViewHover.animate()
                .translationX(binding.textViewHover.width.toFloat())
                .alpha(1.0f)
        }, 250.toLong())
    }
}

package gr.fellow.fellow_traveller.ui.search.fragments

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gr.fellow.fellow_traveller.databinding.FragmentSuccessTripBookBinding
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel


class SuccessTripBookFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private var _binding: FragmentSuccessTripBookBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSuccessTripBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Handler().postDelayed({
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
                        searchTripViewModel.finish()

                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
        }, 500.toLong())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
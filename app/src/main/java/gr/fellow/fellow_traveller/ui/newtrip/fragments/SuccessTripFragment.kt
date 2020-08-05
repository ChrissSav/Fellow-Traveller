package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gr.fellow.fellow_traveller.databinding.FragmentSuccessTripBinding
import kotlinx.android.synthetic.main.fragment_change_password.*


class SuccessTripFragment : Fragment() {

    private var _binding: FragmentSuccessTripBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler()
        handler.postDelayed({
            binding.SuccessActivityTextViewHover .animate()
                .translationX( binding.SuccessActivityTextViewHover.width.toFloat())
                .alpha(1.0f)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        activity?.finish()
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
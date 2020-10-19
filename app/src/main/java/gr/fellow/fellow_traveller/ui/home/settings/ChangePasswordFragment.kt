package gr.fellow.fellow_traveller.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gr.fellow.fellow_traveller.databinding.FragmentChangePasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed


class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ActivityChangePasswordBackButton.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
package gr.fellow.fellow_traveller.ui.home.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentAccountBinding
import gr.fellow.fellow_traveller.databinding.FragmentAccountSettingsBinding
import gr.fellow.fellow_traveller.databinding.FragmentBaseSettingsBinding


class AccountSettingsFragment : Fragment() {


    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentPriceBinding
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


class PriceFragment : Fragment() {
    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPriceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        if (newTripViewModel.price.value != null)
            binding.AddPriceFragmentEditTextPrice.setText(newTripViewModel.price.value.toString())

        newTripViewModel.price.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.next_fragment)
        })

        binding.ImageButtonNext.setOnClickListener {
            if (binding.AddPriceFragmentEditTextPrice.text.isEmpty()) {
                newTripViewModel.setPrice("0".toFloat())
            } else {
                newTripViewModel.setPrice(
                    binding.AddPriceFragmentEditTextPrice.text.toString().toFloat()
                )
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
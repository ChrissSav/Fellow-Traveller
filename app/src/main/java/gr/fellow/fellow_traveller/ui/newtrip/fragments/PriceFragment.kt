package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPriceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        newTripViewModel.price.observe(viewLifecycleOwner, Observer {
            binding.AddPriceFragmentEditTextPrice.setText(it.toString())
            updateTotal(it)
        })

        binding.ImageButtonNext.setOnClickListener {
            navController.navigate(R.id.next_fragment)
        }


        binding.AddPriceFragmentEditTextPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                var price: Float = "0".toFloat()
                if (charSequence.toString().trim().isNotEmpty())
                    price = binding.AddPriceFragmentEditTextPrice.text.toString().toFloat()
                updateTotal(price)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun updateTotal(price: Float) {
        binding.totalPrice.text = (price * newTripViewModel.seats.value!!).toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (binding.AddPriceFragmentEditTextPrice.text.toString().trim().isNotEmpty())
            newTripViewModel.setPrice(
                binding.AddPriceFragmentEditTextPrice.text.toString().toFloat()
            )
        _binding = null
    }
}
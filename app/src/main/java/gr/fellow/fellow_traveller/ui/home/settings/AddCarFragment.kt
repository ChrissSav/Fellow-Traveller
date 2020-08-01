package gr.fellow.fellow_traveller.ui.home.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentAddCarBinding
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.utils.isValidPlate


class AddCarFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!
    private var textErrors = mutableListOf<TextView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textErrors.add(binding.textViewBrandError)
        textErrors.add(binding.textViewModelError)
        textErrors.add(binding.textViewBrandError)
        textErrors.add(binding.textViewColorError)
        textErrors.add(binding.textViewPlateError)

        binding.closeButtonAddCarSettings.setOnClickListener {
            activity?.onBackPressed()
        }


        binding.EditTextBrand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().length > 2)
                    binding.textViewBrandError.visibility = View.INVISIBLE
                else
                    binding.textViewBrandError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.EditTextModel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().length > 2)
                    binding.textViewModelError.visibility = View.INVISIBLE
                else
                    binding.textViewModelError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.EditTextPlate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (isValidPlate(charSequence.toString()))
                    binding.textViewPlateError.visibility = View.INVISIBLE
                else
                    binding.textViewPlateError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.EditTextColor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().length > 2)
                    binding.textViewColorError.visibility = View.INVISIBLE
                else
                    binding.textViewColorError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.buttonAdd.setOnClickListener {
            val brand = binding.EditTextBrand.text.toString()
            val model = binding.EditTextModel.text.toString()
            val plate = binding.EditTextPlate.text.toString()
            val color = binding.EditTextColor.text.toString()

            if (checkErrors()) {
                homeViewModel.addCar(brand, model, plate, color)
            }

        }


        homeViewModel.addCarResult.observe(viewLifecycleOwner, Observer {
            activity?.onBackPressed()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun checkErrors(): Boolean {
        for (error in textErrors)
            if (error.visibility == View.VISIBLE)
                return false
        return true


    }

}
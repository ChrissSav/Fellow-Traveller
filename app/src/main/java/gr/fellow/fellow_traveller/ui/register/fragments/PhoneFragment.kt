package gr.fellow.fellow_traveller.ui.register.fragments

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
import gr.fellow.fellow_traveller.databinding.FragmentPhoneBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import gr.fellow.fellow_traveller.utils.isValidPhone
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PhoneFragment : Fragment() {

    private val registerViewModel: RegisterViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        binding.EditTextPhone.setText(registerViewModel.phone.value)


        binding.ButtonClear.setOnClickListener {
            binding.EditTextPhone.text = null
        }



        binding.EditTextPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty())
                    binding.ButtonClear.visibility = View.VISIBLE
                else
                    binding.ButtonClear.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.ImageButtonNext.setOnClickListener {
            if (isValidPhone( binding.EditTextPhone.text.toString())) {
                registerViewModel.checkUserPhone( binding.EditTextPhone.text.toString())
            } else {
                createSnackBar(view, resources.getString(R.string.INVALID_PHONE_FORMAT))
            }
        }

        registerViewModel.phone.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.next_fragment)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
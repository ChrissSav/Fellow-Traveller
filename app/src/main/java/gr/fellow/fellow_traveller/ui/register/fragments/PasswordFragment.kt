package gr.fellow.fellow_traveller.ui.register.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentEmailBinding
import gr.fellow.fellow_traveller.databinding.FragmentPasswordBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel


class PasswordFragment : Fragment() {

    private val registerViewModel: RegisterViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        binding.password.setText(registerViewModel.password.value)
        binding.passwordConfirm.setText(registerViewModel.password.value)



        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    binding.displayPassword.visibility = View.VISIBLE
                else
                    binding.displayPassword.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.passwordConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().trim().isNotEmpty())
                    binding.displayPasswordConfitm.visibility = View.VISIBLE
                else
                    binding.displayPasswordConfitm.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })




        binding.displayPassword.setOnClickListener {
            if (binding.password.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


            }
            binding.password.setSelection(binding.password.length())

        }



        binding.displayPasswordConfitm.setOnClickListener {
            if (binding.passwordConfirm.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.passwordConfirm.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.passwordConfirm.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


            }
            binding.passwordConfirm.setSelection(binding.passwordConfirm.length())

        }

        binding.ImageButtonNext.setOnClickListener {
            if (binding.password.text.length >= 2) {
                if (binding.password.text.toString() == binding.passwordConfirm.text.toString()) {
                    registerViewModel.storePassword(binding.password.text.toString())
                } else {
                    createSnackBar(view, resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_PASSWORD_COMPLEXITY_LENGTH))

            }
        }

        registerViewModel.password.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.next_fragment)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
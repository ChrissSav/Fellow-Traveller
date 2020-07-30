package gr.fellow.fellow_traveller.ui.main.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.WithFragmentBindings
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.LoginViewModel
import gr.fellow.fellow_traveller.ui.register.RegisterActivity


@WithFragmentBindings
class LoginFragment : Fragment() {


    private val loginViewModel: LoginViewModel by activityViewModels()

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        binding.buttonLogin.setOnClickListener {
            context?.let { it1 -> hideKeyboardFrom(it1, binding.root) }
            if (binding.emailEditText.text.isEmpty() && binding.passwordEditText.text.isEmpty()) {
                createSnackBar(view, resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            } else {
                loginViewModel.login(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
        }

        loginViewModel.result.observe(viewLifecycleOwner, Observer {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
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

        binding.displayPassword.setOnClickListener {
            if (binding.passwordEditText.inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD


            }
            binding.passwordEditText.setSelection(binding.passwordEditText.length())

        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private  fun hideKeyboardFrom(context: Context, view: View) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
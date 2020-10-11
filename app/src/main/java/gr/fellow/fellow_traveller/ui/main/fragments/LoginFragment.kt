package gr.fellow.fellow_traveller.ui.main.fragments

import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.hideKeyboard
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.LoginViewModel
import gr.fellow.fellow_traveller.ui.register.RegisterActivity
import gr.fellow.fellow_traveller.ui.startActivity
import gr.fellow.fellow_traveller.ui.startActivityClearStack


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    private val viewModel: LoginViewModel by activityViewModels()


    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            startActivityClearStack(HomeActivity::class)
        })
    }

    override fun setUpViews() {

        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            if (binding.emailEditText.editText.text.isEmpty() && binding.passwordEditText.text.isEmpty()) {
                createAlerter(resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            } else {
                viewModel.login(
                    binding.emailEditText.editText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            }
            navController?.navigate(R.id.destination_trips_takes_part)

        }



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
            startActivity(RegisterActivity::class)
        }
    }


}
package gr.fellow.fellow_traveller.ui.forgotPassword.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentResetChangePasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.displayPasswordSuggestions
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordViewModel
import gr.fellow.fellow_traveller.utils.PasswordStrengthCalculator


@AndroidEntryPoint
class ResetChangePasswordFragment : BaseFragment<FragmentResetChangePasswordBinding>() {


    private val viewModel: ForgotPasswordViewModel by activityViewModels()
    private lateinit var passwordStrengthCalculator: PasswordStrengthCalculator
    private var color: Int = R.color.weak

    override fun getViewBinding(): FragmentResetChangePasswordBinding =
        FragmentResetChangePasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {


        viewModel.password.observe(viewLifecycleOwner, Observer {
            binding.password.text = it
            binding.passwordConfirm.text = it
        })

        passwordStrengthCalculator.strengthColor.observe(this, { strengthColor ->
            color = strengthColor
        })

        passwordStrengthCalculator.lowerCase.observe(this, { value ->
            displayPasswordSuggestions(value, binding.lowerCaseTxt)
        })
        passwordStrengthCalculator.upperCase.observe(this, { value ->
            displayPasswordSuggestions(value, binding.upperCaseTxt)
        })
        passwordStrengthCalculator.digit.observe(this, { value ->
            displayPasswordSuggestions(value, binding.digitTxt)
        })
        passwordStrengthCalculator.specialChar.observe(this, { value ->
            displayPasswordSuggestions(value, binding.specialCharTxt)
        })
    }

    override fun setUpViews() {


        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }

        binding.buttonNext.setOnClickListener {

            hideKeyboard()
            if (binding.password.isCorrect() && binding.passwordConfirm.isCorrect()) {
                val pass = binding.password.text.toString()
                val passConfirm = binding.passwordConfirm.text.toString()
                if (pass == passConfirm) {
                    viewModel.storePassword(pass)
                    findNavController()?.navigate(R.id.action_resetChangePasswordFragment_to_forgotPasswordNumberFragment)
                } else {
                    binding.passwordConfirm.setError(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            }
        }



    }


}
package gr.fellow.fellow_traveller.ui.register.fragments

import android.text.Editable
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentPasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.displayPasswordSuggestions
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import gr.fellow.fellow_traveller.ui.views.FellowEditTextActionListener
import gr.fellow.fellow_traveller.utils.PasswordStrengthCalculator


@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()
    private lateinit var passwordStrengthCalculator: PasswordStrengthCalculator

    private var color: Int = R.color.weak

    override fun getViewBinding(): FragmentPasswordBinding =
        FragmentPasswordBinding.inflate(layoutInflater)

    override fun setUpObservers() {

        viewModel.password.observe(viewLifecycleOwner, {
            findNavController()?.navigate(R.id.action_passwordFragment_to_accountFragment)
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

        passwordStrengthCalculator = PasswordStrengthCalculator()

        binding.password.text = viewModel.password.value
        binding.passwordConfirm.text = viewModel.password.value


        binding.password.fellowEditTextActionListener = object : FellowEditTextActionListener {
            override fun onTextChange(value: Editable?) {
                passwordStrengthCalculator.onTextChanged(value)
            }
        }


        binding.ImageButtonNext.button.setOnClickListener {
            hideKeyboard()
            if (binding.password.isCorrect() and binding.passwordConfirm.isCorrect()) {
                val pass = binding.password.text.toString()
                val passConfirm = binding.passwordConfirm.text.toString()
                if (pass == passConfirm) {
                    viewModel.storePassword(pass)
                } else {
                    binding.passwordConfirm.setError(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            }
        }

    }


}
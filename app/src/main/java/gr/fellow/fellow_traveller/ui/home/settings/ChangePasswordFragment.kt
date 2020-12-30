package gr.fellow.fellow_traveller.ui.home.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChangePasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.displayPasswordSuggestions
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.utils.PasswordStrengthCalculator


@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var passwordStrengthCalculator: PasswordStrengthCalculator
    private var color: Int = R.color.weak


    override fun getViewBinding(): FragmentChangePasswordBinding =
        FragmentChangePasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.changePassword.observe(viewLifecycleOwner, Observer {
            createToast(getString(R.string.password_change_success))
            onBackPressed()
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
            if (binding.previousPassword.isCorrect() and binding.password.isCorrect() and binding.passwordConfirm.isCorrect()) {
                val pass = binding.password.text.toString()
                val passConfirm = binding.passwordConfirm.text.toString()
                if (pass == passConfirm) {
                    viewModel.changePassword(binding.previousPassword.text.toString(), pass)
                } else {
                    binding.passwordConfirm.setError(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            }
        }


    }


}
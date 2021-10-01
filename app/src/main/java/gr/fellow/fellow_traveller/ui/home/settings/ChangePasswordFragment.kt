package gr.fellow.fellow_traveller.ui.home.settings

import android.text.Editable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChangePasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.views.FellowEditTextNewActionListener
import gr.fellow.fellow_traveller.utils.PasswordStrengthCalculator


@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var passwordStrengthCalculator: PasswordStrengthCalculator


    override fun getViewBinding(): FragmentChangePasswordBinding =
        FragmentChangePasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.changePassword.observe(viewLifecycleOwner, Observer {
            createToast(getString(R.string.password_change_success))
            onBackPressed()
        })


        passwordStrengthCalculator.strength.observe(viewLifecycleOwner, { value ->
            //binding.passwordStr.setTextBackTint(value.textInt, value.colorInt)
        })

    }

    override fun setUpViews() {

        passwordStrengthCalculator = PasswordStrengthCalculator()
        binding.ImageButtonBack.button.setOnClickListener {
            onBackPressed()
        }


        binding.password.fellowEditTextNewActionListener = object : FellowEditTextNewActionListener {
            override fun onTextChange(value: Editable?) {
                passwordStrengthCalculator.onTextChanged(value)
            }
        }

        binding.save.setOnClickListener {

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
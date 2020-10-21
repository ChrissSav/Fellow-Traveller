package gr.fellow.fellow_traveller.ui.forgotPassword.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentResetChangePasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordViewModel


@AndroidEntryPoint
class ResetChangePasswordFragment : BaseFragment<FragmentResetChangePasswordBinding>() {


    private val viewModel: ForgotPasswordViewModel by activityViewModels()


    override fun getViewBinding(): FragmentResetChangePasswordBinding =
        FragmentResetChangePasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {


        viewModel.password.observe(viewLifecycleOwner, Observer {
            binding.password.text = it
            binding.passwordConfirm.text = it
        })
    }

    override fun setUpViews() {


        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }

        binding.buttonNext.setOnClickListener {
            val pass = binding.password.text.toString()
            val passConfirm = binding.passwordConfirm.text.toString()
            hideKeyboard()
            if (pass.length >= 2) {
                if (pass == passConfirm) {
                    viewModel.storePassword(pass)
                    findNavController()?.navigate(R.id.action_resetChangePasswordFragment_to_forgotPasswordNumberFragment)
                } else {
                    createAlerter(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            } else {
                createAlerter(resources.getString(R.string.ERROR_PASSWORD_COMPLEXITY_LENGTH))
            }
        }

        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }


    }


}
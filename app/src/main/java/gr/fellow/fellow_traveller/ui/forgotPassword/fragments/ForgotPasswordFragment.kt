package gr.fellow.fellow_traveller.ui.forgotPassword.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentForgotPasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordViewModel
import gr.fellow.fellow_traveller.utils.isValidEmail


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {


    private val viewModel: ForgotPasswordViewModel by activityViewModels()


    override fun getViewBinding(): FragmentForgotPasswordBinding =
        FragmentForgotPasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.successForgotRequest.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.action_forgotPasswordFragment2_to_resetChangePasswordFragment)
        })

    }

    override fun setUpViews() {

        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }


        binding.recovery.setOnClickListener {
            hideKeyboard()
            val email = binding.email.text.toString().trim()
            if (isValidEmail(email)) {
                viewModel.forgotPassword(email)
            } else {
                createAlerter(resources.getString(R.string.ERROR_INVALID_EMAIL_FORMAT))
            }
        }

    }


}
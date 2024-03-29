package gr.fellow.fellow_traveller.ui.forgotPassword.fragments


import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentForgotPasswordNumberBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.pasteTextFromClipboard
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordViewModel


@AndroidEntryPoint
class ForgotPasswordNumberFragment : BaseFragment<FragmentForgotPasswordNumberBinding>() {


    private val viewModel: ForgotPasswordViewModel by activityViewModels()


    override fun getViewBinding(): FragmentForgotPasswordNumberBinding =
        FragmentForgotPasswordNumberBinding.inflate(layoutInflater)


    override fun setUpObservers() {}

    override fun setUpViews() {
        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }

        binding.buttonResend.setOnClickListener {
            viewModel.forgotPassword()
        }

        binding.pinView.requestPinEntryFocus()

        binding.finish.setOnClickListener {
            val code = binding.pinView.value.toString()
            if (code.length != 6) {
                createAlerter(getString(R.string.password_missing))
            } else {
                viewModel.resetPassword(code)
            }
        }

        binding.paste.setOnClickListener {
            binding.pinView.value = activity?.pasteTextFromClipboard()
        }

    }


}
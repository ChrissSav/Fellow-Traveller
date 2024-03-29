package gr.fellow.fellow_traveller.ui.forgotPassword.fragments

import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentForgotPasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.getResentEmail
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordViewModel
import javax.inject.Inject


@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel: ForgotPasswordViewModel by activityViewModels()


    override fun getViewBinding(): FragmentForgotPasswordBinding =
        FragmentForgotPasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.successForgotRequest.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.action_forgotPasswordFragment2_to_resetChangePasswordFragment)
        })

    }

    override fun setUpViews() {

        binding.email.text = getResentEmail(sharedPreferences)

        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }


        binding.recovery.setOnClickListener {
            hideKeyboard()
            if (binding.email.isCorrect()) {
                val email = binding.email.text.toString().trim()
                viewModel.forgotPassword(email)
            }
        }

    }


}
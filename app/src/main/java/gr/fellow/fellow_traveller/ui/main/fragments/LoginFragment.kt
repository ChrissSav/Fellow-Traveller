package gr.fellow.fellow_traveller.ui.main.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.startActivity
import gr.fellow.fellow_traveller.ui.extensions.startActivityClearStack
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordActivity
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainViewModel


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    private val viewModel: MainViewModel by activityViewModels()


    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            startActivityClearStack(HomeActivity::class)
        })
    }

    override fun setUpViews() {

        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            if (binding.email.isCorrect() && binding.password.isCorrect()) {
                viewModel.login(binding.email.text.toString(), binding.password.text.toString())
            }

        }

        binding.forgotPassword.setOnClickListener {
            startActivity(ForgotPasswordActivity::class)
        }


        binding.ImageButtonBack.setOnClickListener {
            onBackPressed()
        }
    }


}
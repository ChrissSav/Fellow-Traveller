package gr.fellow.fellow_traveller.ui.main.fragments

import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.forgotPassword.ForgotPasswordActivity
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainViewModel
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: MainViewModel by activityViewModels()


    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            setResentEmail(sharedPreferences, binding.email.text)
            startActivityClearStack(HomeActivity::class)
        })
    }

    override fun setUpViews() {

        binding.email.text = getResentEmail(sharedPreferences)

        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            if (binding.email.isCorrect() and binding.password.isCorrect()) {
                viewModel.login(binding.email.text.toString(), binding.password.text.toString())
            }

        }



        binding.forgotPassword.setOnClickListener {
            startActivity(ForgotPasswordActivity::class)
        }


        binding.ImageButtonBack.button.setOnClickListener {
            onBackPressed()
        }
    }


}
package gr.fellow.fellow_traveller.ui.main.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentLoginBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.hideKeyboard
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.LoginViewModel
import gr.fellow.fellow_traveller.ui.startActivityClearStack


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    private val viewModel: LoginViewModel by activityViewModels()


    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            startActivityClearStack(HomeActivity::class)
        })
    }

    override fun setUpViews() {

        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            if (binding.email.text.toString().isEmpty() && binding.password.text.toString()
                    .isEmpty()
            ) {
                createAlerter(resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            } else {
                viewModel.login(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            }

        }


        /* binding.registerButton.setOnClickListener {
             startActivity(RegisterActivity::class)
         }*/
    }


}
package gr.fellow.fellow_traveller.ui.home.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentChangePasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.createToast
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel


@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()


    override fun getViewBinding(): FragmentChangePasswordBinding =
        FragmentChangePasswordBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.changePassword.observe(viewLifecycleOwner, Observer {
            createToast(getString(R.string.password_change_success))
            onBackPressed()
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
                    createAlerter(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            }
        }


    }


}
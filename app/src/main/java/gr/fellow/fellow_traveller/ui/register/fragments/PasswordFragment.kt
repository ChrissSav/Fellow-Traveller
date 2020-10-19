package gr.fellow.fellow_traveller.ui.register.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentPasswordBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.hideKeyboard
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()


    override fun getViewBinding(): FragmentPasswordBinding =
        FragmentPasswordBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.password.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.next_fragment)
        })
    }

    override fun setUpViews() {
        binding.password.text = viewModel.password.value
        binding.passwordConfirm.text = viewModel.password.value


        binding.ImageButtonNext.setOnClickListener {
            val pass = binding.password.text.toString()
            val passConfirm = binding.passwordConfirm.text.toString()
            hideKeyboard()
            if (pass.length >= 2) {
                if (pass == passConfirm) {
                    viewModel.storePassword(pass)
                } else {
                    createAlerter(resources.getString(R.string.ERROR_PASSWORD_DO_NOT_MATCH))
                }
            } else {
                createAlerter(resources.getString(R.string.ERROR_PASSWORD_COMPLEXITY_LENGTH))

            }
        }

    }

}
package gr.fellow.fellow_traveller.ui.register.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentAccountInfoBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel

@AndroidEntryPoint
class AccountInfoFragment : BaseFragment<FragmentAccountInfoBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding(): FragmentAccountInfoBinding =
        FragmentAccountInfoBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.finish.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.action_accountFragment_to_successRegistrationFragment)
        })
        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            binding.firstName.text = it.first
            binding.lastName.text = it.second
        })
    }

    override fun onDestroyView() {
        viewModel.storeUserInfo(binding.firstName.text, binding.lastName.text)
        super.onDestroyView()
    }

    override fun setUpViews() {

        binding.ImageButtonNext.button.setOnClickListener {
            if (binding.firstName.isCorrect() && binding.lastName.isCorrect()) {
                viewModel.storeUserInfo(binding.firstName.text.toString(), binding.lastName.text.toString())
                viewModel.registerUser()
            }
        }
    }


}

package gr.fellow.fellow_traveller.ui.register.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentAccountInfoBinding
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import gr.fellow.fellow_traveller.ui.startActivityClearStack

@AndroidEntryPoint
class AccountInfoFragment : BaseFragment<FragmentAccountInfoBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()


    override fun getViewBinding(): FragmentAccountInfoBinding =
        FragmentAccountInfoBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.finish.observe(viewLifecycleOwner, Observer {
            startActivityClearStack(HomeActivity::class)
        })
    }

    override fun setUpViews() {
        binding.firstName.text = viewModel.userInfo.value?.first
        binding.lastName.text = viewModel.userInfo.value?.second


        binding.ImageButtonNext.setOnClickListener {
            viewModel.storeUserInfo(binding.firstName.text.toString(), binding.lastName.text.toString())
            viewModel.registerUser()
        }
    }

}
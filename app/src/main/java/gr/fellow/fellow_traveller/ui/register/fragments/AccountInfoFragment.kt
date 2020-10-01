package gr.fellow.fellow_traveller.ui.register.fragments

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentAccountInfoBinding
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel

@AndroidEntryPoint
class AccountInfoFragment : BaseFragment<FragmentAccountInfoBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()


    override fun getViewBinding(): FragmentAccountInfoBinding =
        FragmentAccountInfoBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.finish.observe(viewLifecycleOwner, Observer {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
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
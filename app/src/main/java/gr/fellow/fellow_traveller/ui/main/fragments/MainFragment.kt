package gr.fellow.fellow_traveller.ui.main.fragments

import gr.fellow.fellow_traveller.BuildConfig
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentMainBinding
import gr.fellow.fellow_traveller.ui.extensions.openUrl
import gr.fellow.fellow_traveller.ui.extensions.setTextHtml
import gr.fellow.fellow_traveller.ui.extensions.startActivity
import gr.fellow.fellow_traveller.ui.register.RegisterActivity


class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getViewBinding(): FragmentMainBinding =
        FragmentMainBinding.inflate(layoutInflater)

    override fun setUpObservers() {}

    override fun setUpViews() {
        binding.buttonLogin.setOnClickListener {
            navController?.navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(RegisterActivity::class)
        }

        binding.tos.setTextHtml(getString(R.string.agree_with_fellow_tos))


        binding.tos.setOnClickListener {
            activity?.openUrl(BuildConfig.FELLOW_TOS_URL)
        }
    }

}
package gr.fellow.fellow_traveller.ui.register.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentEmailBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.hideKeyboard
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import gr.fellow.fellow_traveller.utils.isValidEmail

@AndroidEntryPoint
class EmailFragment : BaseFragment<FragmentEmailBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding(): FragmentEmailBinding =
        FragmentEmailBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.email.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.next_fragment)
        })
    }

    override fun setUpViews() {
        binding.email.text = viewModel.email.value



        binding.ImageButtonNext.setOnClickListener {
            hideKeyboard()
            val email = binding.email.text.toString().trim()
            if (isValidEmail(email)) {
                viewModel.checkUserEmail(email)
            } else {
                createAlerter(resources.getString(R.string.ERROR_INVALID_EMAIL_FORMAT))
            }
        }
    }

}
package gr.fellow.fellow_traveller.ui.register.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentPhoneBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import gr.fellow.fellow_traveller.utils.isValidPhone

@AndroidEntryPoint
class PhoneFragment : BaseFragment<FragmentPhoneBinding>() {

    private val viewModel: RegisterViewModel by activityViewModels()


    override fun getViewBinding(): FragmentPhoneBinding =
        FragmentPhoneBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.phone.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.next_fragment)
        })
    }

    override fun setUpViews() {

        binding.EditTextPhone.setText(viewModel.phone.value)

        binding.ButtonClear.setOnClickListener {
            binding.EditTextPhone.text = null
        }

        binding.EditTextPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty())
                    binding.ButtonClear.visibility = View.VISIBLE
                else
                    binding.ButtonClear.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.ImageButtonNext.setOnClickListener {
            if (isValidPhone(binding.EditTextPhone.text.toString())) {
                viewModel.checkUserPhone(binding.EditTextPhone.text.toString())
            } else {
                createAlerter(resources.getString(R.string.INVALID_PHONE_FORMAT))
            }
        }
    }
}
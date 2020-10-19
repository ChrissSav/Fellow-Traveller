package gr.fellow.fellow_traveller.ui.newtrip.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentMessageBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding>() {
    private val viewModel: NewTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentMessageBinding =
        FragmentMessageBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            binding.AddMessageFragmentEditText.setText(it)
        })
    }

    override fun setUpViews() {
        binding.ImageButtonNext.root.setOnClickListener {
            viewModel.setMessage(binding.AddMessageFragmentEditText.text.toString().trim())

            if (viewModel.message.value != null)
                findNavController()?.navigate(R.id.next_fragment)
        }
    }

}
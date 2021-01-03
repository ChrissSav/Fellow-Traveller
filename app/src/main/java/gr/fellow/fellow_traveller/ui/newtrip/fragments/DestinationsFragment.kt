package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentDestinationsBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


@AndroidEntryPoint
class DestinationsFragment : BaseFragment<FragmentDestinationsBinding>() {

    private val viewModel: NewTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentDestinationsBinding =
        FragmentDestinationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.destinationFrom.observe(viewLifecycleOwner, {
            binding.editTextFrom.text = it.title
        })

        viewModel.destinationTo.observe(viewLifecycleOwner, {
            binding.editTextTo.text = it.title
        })
    }

    override fun setUpViews() {

        with(binding) {

            editTextFrom.addOnClickListener {
                startActivityForResultWithFade(SelectLocationActivity::class, 1)
            }

            editTextTo.addOnClickListener {
                startActivityForResultWithFade(SelectLocationActivity::class, 2)
            }

            ImageButtonNext.setOnClickListener {
                if (editTextFrom.isCorrect() and editTextTo.isCorrect())
                    findNavController()?.navigate(R.id.action_destinationsFragment_to_dateTimeFragment)

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                viewModel.setDestinationFrom(id, title)
            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                viewModel.setDestinationTo(id, title)

            }

        }
    }

}
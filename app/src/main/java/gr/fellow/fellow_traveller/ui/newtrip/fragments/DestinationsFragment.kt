package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentDestinationsBinding
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.startActivityForResultWithFade


@AndroidEntryPoint
class DestinationsFragment : BaseFragment<FragmentDestinationsBinding>() {

    private val viewModel: NewTripViewModel by activityViewModels()


    override fun getViewBinding(): FragmentDestinationsBinding =
        FragmentDestinationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.destinationFrom.observe(viewLifecycleOwner, Observer {
            binding.editTextFrom.text = it.title
        })

        viewModel.destinationTo.observe(viewLifecycleOwner, Observer {
            binding.editTextTo.text = it.title
        })
    }

    override fun setUpViews() {
        binding.editTextFrom.onClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 1)
        }

        binding.editTextTo.onClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 2)
        }

        binding.ImageButtonNext.root.setOnClickListener {

            when {
                viewModel.destinationFrom.value == null -> {
                    viewModel.setError(R.string.ERROR_SELECT_DEST_FROM)
                }
                viewModel.destinationTo.value == null -> {
                    viewModel.setError(R.string.ERROR_SELECT_DEST_TO)
                }
                else -> {
                    findNavController()?.navigate(R.id.next_fragment)
                }
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
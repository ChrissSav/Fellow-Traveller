package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentPickUpBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


@AndroidEntryPoint
class PickUpFragment : BaseFragment<FragmentPickUpBinding>() {

    private val viewModel: NewTripViewModel by activityViewModels()

    override fun getViewBinding(): FragmentPickUpBinding =
        FragmentPickUpBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.destinationPickUp.observe(viewLifecycleOwner, Observer {
            binding.pickUpButton.text = it.title
        })
    }

    override fun setUpViews() {
        binding.pickUpButton.onClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 1)
        }

        binding.ImageButtonNext.root.setOnClickListener {
            if (viewModel.destinationPickUp.value == null) {
                viewModel.setError(R.string.ERROR_SELECT_DEST_PICK_UP)
            } else {
                findNavController()?.navigate(R.id.next_fragment)
            }
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                viewModel.setDestinationPickUp(id, title)
            }
        }
    }

}
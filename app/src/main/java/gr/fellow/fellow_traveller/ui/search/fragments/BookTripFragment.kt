package gr.fellow.fellow_traveller.ui.search.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBookTripBinding
import gr.fellow.fellow_traveller.ui.dialogs.TripBookConfirmDialog
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel

@AndroidEntryPoint
class BookTripFragment : BaseFragment<FragmentBookTripBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var index: Int = 0
    private var tripId = "0"
    private lateinit var tripBookConfirmDialog: TripBookConfirmDialog


    override fun getViewBinding(): FragmentBookTripBinding =
        FragmentBookTripBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.resultTrips.value?.get(index)?.let {
            with(it) {
                tripId = it.id
                binding.startTextView.text = destFrom.title
                binding.endTextView.text = destTo.title
                /*binding.date.text = getDateFormat()
                binding.time.text = getTimeFormat()
                binding.seatsTextView.text = (maxBags - currentBags).toString()
                binding.petsSwitch.isEnabled = hasPet
                binding.priceTextView.text = getString(R.string.price, price.toString())
                binding.bagsIncreaseSection.setUpperLimit(maxBags - currentBags)*/

            }
        }


        viewModel.tripBook.observe(viewLifecycleOwner, Observer {
            findNavController()?.navigate(R.id.action_bookTripFragment_to_successTripBookFragment)
        })
    }

    override fun setUpViews() {
        with(binding) {
            closeButton.setOnClickListener {
                onBackPressed()
            }

            bookButton.setOnClickListener {
                // openDialog()

            }
        }

        binding.petsSwitch.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.havePetTextView.text = resources.getString(R.string.have_pet)
            } else {
                binding.havePetTextView.text = resources.getString(R.string.have_not_pet)
            }
        }
    }

    override fun handleIntent() {
        index = requireArguments().getInt("indexTrip")
    }


    /*private fun openDialog() {
        activity?.supportFragmentManager?.let {
            tripBookConfirmDialog = TripBookConfirmDialog(requireActivity()) { result ->
                if (result)
                    viewModel.bookTrip(tripId, binding.bagsIncreaseSection.currentNum, binding.petsSwitch.isChecked)

                tripBookConfirmDialog.dismiss()
            }
            tripBookConfirmDialog.show(it, "example dialog")
        }

    }*/

}
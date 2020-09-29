package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentBookTripBinding
import gr.fellow.fellow_traveller.ui.dialogs.TripBookConfirmDialog
import gr.fellow.fellow_traveller.ui.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel


class BookTripFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentBookTripBinding? = null
    private val binding get() = _binding!!
    private var index: Int = 0
    private var tripId = 0
    private lateinit var tripBookConfirmDialog: TripBookConfirmDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentBookTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = requireArguments().getInt("indexTrip")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        searchTripViewModel.resultTrips.value?.get(index)?.let {
            with(it) {
                tripId = it.id
                binding.startTextView.text = destFrom.title
                binding.endTextView.text = destTo.title
                binding.date.text = getDateFormat()
                binding.time.text = getTimeFormat()
                binding.seatsTextView.text = (maxBags - currentBags).toString()
                binding.petsSwitch.isEnabled = hasPet
                binding.priceTextView.text = getString(R.string.price, price.toString())

                binding.bagsIncreaseSection.setUpperLimit(maxBags - currentBags)

                binding.petsSwitch.setOnCheckedChangeListener { _, b ->
                    if (b) {
                        binding.havePetTextView.text = resources.getString(R.string.have_pet)
                    } else {
                        binding.havePetTextView.text = resources.getString(R.string.have_not_pet)
                    }
                }
            }
        }

        with(binding) {
            closeButton.setOnClickListener {
                onBackPressed()
            }

            bookButton.setOnClickListener {
                openDialog()

            }
        }


        searchTripViewModel.tripBook.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.action_bookTripFragment_to_successTripBookFragment)
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun openDialog() {
        activity?.supportFragmentManager?.let {
            tripBookConfirmDialog = TripBookConfirmDialog(requireActivity()) { result ->
                if (result)
                    searchTripViewModel.bookTrip(tripId, binding.bagsIncreaseSection.currentNum, binding.petsSwitch.isChecked)

                tripBookConfirmDialog.dismiss()
            }
            tripBookConfirmDialog.show(it, "example dialog")
        }

    }


}
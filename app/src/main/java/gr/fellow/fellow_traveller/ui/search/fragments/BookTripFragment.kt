package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentBookTripBinding
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel


class BookTripFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentBookTripBinding? = null
    private val binding get() = _binding!!
    private var index: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                binding.startTextView.text = destFrom.title
                binding.endTextView.text = destTo.title
                binding.date.text = getDateFormat()
                binding.time.text = getTimeFormat()
                binding.seatsTextView.text = (maxBags - currentBags).toString()
                binding.petsSwitch.isEnabled = hasPet
                binding.priceTextView.text = getString(R.string.price, price.toString())

                binding.bagsIncreaseSection.setUpperLimit(maxBags - currentBags)


                /*binding.bagsIncreaseSection.pickButtonActionListener = object : PickButtonActionListener {
                    override fun onPickAction(value: Int) {
                        if (value > resources.getInteger(R.integer.seats_min)) {
                            searchFilters.seatsMin = value
                        } else {
                            searchFilters.seatsMin = null
                        }
                    }
                }*/
            }
        }

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentSearchFilterBinding
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener

class SearchFilterFragment : Fragment() {

    private lateinit var navController: NavController
    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var searchFilters: SearchFilters

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        searchTripViewModel.searchFilter.value?.let {
            searchFilters = it.copy()
        }



        with(searchFilters) {

            binding.seatsPickButton.currentNum = seatsMin ?: resources.getInteger(R.integer.seats_min)
            binding.bagsPickButton.currentNum = bagsMin ?: resources.getInteger(R.integer.bags_min)
            binding.fromRangeKmSeekbar.setMinStartValue((rangeFrom ?: resources.getInteger(R.integer.min_value_in_filters)).toFloat()).apply()
            binding.toRangeKmSeekbar.setMinStartValue((rangeTo ?: resources.getInteger(R.integer.min_value_in_filters)).toFloat()).apply()
            binding.priceRangeSeekbar.setMaxStartValue((priceMax ?: resources.getInteger(R.integer.price_max)).toFloat()).apply()

            binding.priceRangeSeekbar.setMinStartValue(
                if (priceMin != null)
                    if (priceMin == resources.getInteger(R.integer.price_max))
                        "99.9".toFloat()
                    else
                        priceMin!!.toFloat()
                else
                    resources.getInteger(R.integer.min_value_in_filters).toFloat()
            ).apply()

        }

        with(binding) {

            seatsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    if (value > resources.getInteger(R.integer.seats_min)) {
                        searchFilters.seatsMin = value
                    } else {
                        searchFilters.seatsMin = null
                    }
                }
            }

            bagsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    if (value > resources.getInteger(R.integer.bags_min)) {
                        searchFilters.bagsMin = value
                    } else {
                        searchFilters.bagsMin = null
                    }
                }
            }



            resetButton.setOnClickListener {
                searchFilters.reset()
                searchTripViewModel.searchFilter.value?.let { filter ->
                    if (filter != (searchFilters))
                        searchTripViewModel.updateFilter(searchFilters)
                }
                activity?.onBackPressed()
            }

            applyButton.setOnClickListener {
                searchTripViewModel.searchFilter.value?.let { filter ->
                    if (filter != (searchFilters))
                        searchTripViewModel.updateFilter(searchFilters)
                }
                activity?.onBackPressed()
            }
        }

        binding.fromRangeKmSeekbar.setOnSeekbarChangeListener(OnSeekbarChangeListener { value ->
            if (value.toInt() == 0) {
                binding.fromRangeRadiusTv.text = getString(R.string.range_view_all)
                searchFilters.rangeFrom = null
            } else {
                binding.fromRangeRadiusTv.text = getString(R.string.range_text, value.toString())
                searchFilters.rangeFrom = value.toInt()
            }
        })

        binding.toRangeKmSeekbar.setOnSeekbarChangeListener(OnSeekbarChangeListener { value ->
            if (value.toInt() == 0) {
                binding.toRangeKmSeekbarTv.text = getString(R.string.range_view_all)
                searchFilters.rangeTo = null
            } else {
                binding.toRangeKmSeekbarTv.text = getString(R.string.range_text, value.toString())
                searchFilters.rangeTo = value.toInt()
            }
        })



        binding.priceRangeSeekbar.setOnRangeSeekbarChangeListener(OnRangeSeekbarChangeListener { minValue, maxValue ->
            if (minValue.toInt() == resources.getInteger(R.integer.min_value_in_filters) && maxValue.toInt() == resources.getInteger(R.integer.price_max)) {
                binding.priceRangeRadiusTv.text = getString(R.string.range_view_all)
                searchFilters.priceMax = null
                searchFilters.priceMin = null
            } else {
                binding.priceRangeRadiusTv.text = getString(R.string.range_text_price, minValue.toString(), maxValue.toString())
                if (minValue.toInt() != 0)
                    searchFilters.priceMin = minValue.toInt()
                else
                    searchFilters.priceMin = null
                if (maxValue.toInt() != 0)
                    searchFilters.priceMax = maxValue.toInt()
                else
                    searchFilters.priceMax = null

            }
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
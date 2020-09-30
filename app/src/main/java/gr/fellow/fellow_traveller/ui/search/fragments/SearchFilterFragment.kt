package gr.fellow.fellow_traveller.ui.search.fragments

import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchFilterBinding
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.ui.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener

@AndroidEntryPoint
class SearchFilterFragment : BaseFragment<FragmentSearchFilterBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private lateinit var searchFilters: SearchFilters

    override fun getViewBinding(): FragmentSearchFilterBinding =
        FragmentSearchFilterBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.searchFilter.value?.let {
            searchFilters = it.copy()
        }
    }

    override fun setUpViews() {
        binding.closeButton.setOnClickListener {
            onBackPressed()
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
                viewModel.searchFilter.value?.let { filter ->
                    if (filter != (searchFilters))
                        viewModel.updateFilter(searchFilters)
                }
                onBackPressed()
            }

            applyButton.setOnClickListener {
                viewModel.searchFilter.value?.let { filter ->
                    if (filter != (searchFilters))
                        viewModel.updateFilter(searchFilters)
                }
                onBackPressed()
            }


            fromRangeKmSeekbar.setOnSeekbarChangeListener { value ->
                if (value.toInt() == 0) {
                    binding.fromRangeRadiusTv.text = getString(R.string.range_view_all)
                    searchFilters.rangeFrom = null
                } else {
                    binding.fromRangeRadiusTv.text = getString(R.string.range_text, value.toString())
                    searchFilters.rangeFrom = value.toInt()
                }
            }

            toRangeKmSeekbar.setOnSeekbarChangeListener { value ->
                if (value.toInt() == 0) {
                    binding.toRangeKmSeekbarTv.text = getString(R.string.range_view_all)
                    searchFilters.rangeTo = null
                } else {
                    binding.toRangeKmSeekbarTv.text = getString(R.string.range_text, value.toString())
                    searchFilters.rangeTo = value.toInt()
                }
            }



            priceRangeSeekbar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
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
            }
        }
    }

}
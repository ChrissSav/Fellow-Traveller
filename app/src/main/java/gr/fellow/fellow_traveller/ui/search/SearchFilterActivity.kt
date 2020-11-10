package gr.fellow.fellow_traveller.ui.search

import android.app.Activity
import android.content.Intent
import com.google.android.material.datepicker.MaterialDatePicker
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySearchFilterBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.SearchTripPetBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.createToast
import kotlinx.android.synthetic.main.search_trip_filter_bottom_sheet_dialog.*
import java.text.SimpleDateFormat
import java.util.*


class SearchFilterActivity : BaseActivity<ActivitySearchFilterBinding>() {


    private lateinit var petBottomSheetDialog: SearchTripPetBottomSheetDialog
    private lateinit var searchTripFilter: SearchTripFilter



    override fun provideViewBinding(): ActivitySearchFilterBinding =
        ActivitySearchFilterBinding.inflate(layoutInflater)


    override fun handleIntent() {
        intent.getParcelableExtra<SearchTripFilter>("filter")?.let {
            searchTripFilter = it
        }
    }

    override fun setUpObservers() {

    }

    override fun setUpViews() {

        with(binding) {


            /** Initialization **/

            seatsPickButton.currentNum = searchTripFilter.seatsMin ?: resources.getInteger(R.integer.seats_min)
            fromRangeSeekbar.setMinStartValue((searchTripFilter.rangeFrom ?: resources.getInteger(R.integer.min_value_in_filters)).toFloat()).apply()
            toRangeSeekbar.setMinStartValue((searchTripFilter.rangeTo ?: resources.getInteger(R.integer.min_value_in_filters)).toFloat()).apply()
            priceRangeSeekbar.setMinStartValue((searchTripFilter.priceMax ?: resources.getInteger(R.integer.min_value_in_filters)).toFloat()).apply()
            seatsPickButton.currentNum = searchTripFilter.seatsMin ?: 1
            initializePet()

            /** Date Picker Initialization **/
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            val now = Calendar.getInstance()
            builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
            builder.setTitleText("Επιλέξτε εύρος ημερομηνίας για αναζήτηση")
            val picker = builder.build()

            /** OnClicks **/

            date.setOnClickListener {
                picker.show(this@SearchFilterActivity?.supportFragmentManager!!, picker.toString())


            }
            pet.setOnClickListener {
                petBottomSheetDialog = SearchTripPetBottomSheetDialog(this@SearchFilterActivity::onPetItemClickListener)
                petBottomSheetDialog.show(supportFragmentManager, "petBottomSheetDialog")

            }

            backButton.setOnClickListener {
                finish()
            }

            resetButton.setOnClickListener {
                searchTripFilter.reset()
                applyChanges()
            }


            applyButton.setOnClickListener {
                applyChanges()
            }


            /** Handle Changes **/

            //Cancel button on dialog
            picker.addOnNegativeButtonClickListener {
                builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
                builder.build()
                //Long first/second to null
                date.setText("Εύρος ημ/νίας")}

            picker.addOnPositiveButtonClickListener {
                //"The selected date range it  ${it.first} - ${it.second}"
                //Convert Long to Date Format
                date.setText(it.first?.let { it1 -> convertLongToTime(it1) } + " - " + it.second?.let { it1 -> convertLongToTime(it1) }) }

            picker.addOnCancelListener { }
            picker.addOnDismissListener { }




            fromRangeSeekbar.setOnSeekbarChangeListener { value ->
                if (value.toInt() == 0) {
                    binding.fromRangeRadiusTv.setText(getString(R.string.range_view_all))
                    searchTripFilter.rangeFrom = null
                } else {
                    binding.fromRangeRadiusTv.setText(getString(R.string.range_text, value.toString()))
                    searchTripFilter.rangeFrom = value.toInt()
                }
            }

            toRangeSeekbar.setOnSeekbarChangeListener { value ->
                if (value.toInt() == 0) {
                    binding.toRangeRadiusTv.setText(getString(R.string.range_view_all))
                    searchTripFilter.rangeTo = null
                } else {
                    binding.toRangeRadiusTv.setText(getString(R.string.range_text, value.toString()))
                    searchTripFilter.rangeTo = value.toInt()
                }
            }


            priceRangeSeekbar.setOnSeekbarChangeListener { value ->
                if (value.toInt() == 0) {
                    binding.priceTv.setText(getString(R.string.range_view_all))
                    searchTripFilter.priceMax = null
                } else {
                    binding.priceTv.setText(getString(R.string.range_text_price, value.toString()))
                    searchTripFilter.priceMax = value.toInt()
                }
            }
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd MMM")
        return format.format(date)
    }
    private fun applyChanges() {
        val resultIntent = Intent()
        if (binding.seatsPickButton.currentNum != 1)
            searchTripFilter.seatsMin = binding.seatsPickButton.currentNum
        resultIntent.putExtra("filter", searchTripFilter)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun initializePet() {
        if (searchTripFilter.pet == true) {
            binding.pet.setText("Ναί")
        } else if (searchTripFilter.pet == false) {
            binding.pet.setText("Όχι")
        }
    }

    private fun onPetItemClickListener(petAnswerType: PetAnswerType) {

        when (petAnswerType) {
            PetAnswerType.Yes -> {
                searchTripFilter.pet = true
                binding.pet.setText("Ναί")
            }
            PetAnswerType.No -> {
                searchTripFilter.pet = false
                binding.pet.setText("Όχι")
            }
            else -> {
                searchTripFilter.pet = null
                binding.pet.text = null
            }
        }
    }

}
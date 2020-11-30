package gr.fellow.fellow_traveller.ui.search

import android.app.Activity
import android.content.Intent
import android.widget.SeekBar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySearchFilterBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.SearchTripPetBottomSheetDialog
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener
import gr.fellow.fellow_traveller.utils.convertTimestampToFormat
import kotlinx.android.synthetic.main.label_text_component.view.*
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

            seatsPickButton.currentNum = searchTripFilter.seatsMin

            fromRangeSeekbar.progress = ((searchTripFilter.rangeFrom ?: resources.getInteger(R.integer.min_value_in_filters)).toInt())
            updateRangeFrom(fromRangeSeekbar.progress)

            toRangeSeekbar.progress = ((searchTripFilter.rangeTo ?: resources.getInteger(R.integer.min_value_in_filters)).toInt())
            updateRangeTo(toRangeSeekbar.progress)


            priceRangeSeekbar.progress = ((searchTripFilter.priceMax ?: resources.getInteger(R.integer.min_value_in_filters)).toInt())
            updateRangePrice(priceRangeSeekbar.progress)


            initializePet()

            /** Date Picker Initialization **/
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            val now = Calendar.getInstance()
            builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))

            /** Calendars Initialization **/
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.clear()
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val cal = Calendar.getInstance()

            cal.timeInMillis = today
            calendar.timeInMillis = today

            calendar[Calendar.YEAR] = cal[Calendar.YEAR]
            val left = calendar.timeInMillis
            calendar[Calendar.YEAR] = cal[Calendar.YEAR] + 1
            val right = calendar.timeInMillis
            /** Finished initialization of Calender**/


            /** Calendar Constraints**/
            val constraintBuilder = CalendarConstraints.Builder()
            constraintBuilder.setStart(left)
            constraintBuilder.setEnd(right)
            constraintBuilder.setValidator(DateValidatorPointForward.now())


            builder.setCalendarConstraints(constraintBuilder.build())
            builder.setTitleText(getString(R.string.choose_date_range_for_search))
            val picker = builder.build()

            /** OnClicks **/

            date.setOnClickListener {
                picker.show(this@SearchFilterActivity.supportFragmentManager, picker.toString())

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
                date.setText(getString(R.string.date_range))
            }

            picker.addOnPositiveButtonClickListener {
                //"The selected date range it  ${it.first} - ${it.second}"
                //Convert Long to Date Format
                date.setText(it.first?.let { it1 -> convertTimestampToFormat(it1, "dd MMM") } + " - " + it.second?.let { it1 -> convertTimestampToFormat(it1, "dd MMM") })
            }

            picker.addOnCancelListener { }
            picker.addOnDismissListener { }




            seatsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    searchTripFilter.seatsMin = value
                }
            }



            fromRangeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                    updateRangeFrom(value)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })


            toRangeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                    updateRangeTo(value)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

            priceRangeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
                    updateRangePrice(value)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

            NestedScrollView.setOnScrollChangeListener(androidx.core.widget.NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                //Log.i("makis", "scrollY : $scrollY oldScrollY: $oldScrollY")

                if (scrollY < oldScrollY) {
                    // Scrolling up
                    if (!applyButton.isExtended)
                        applyButton.extend()
                    //  Log.i("makis", "Scrolling up")

                } else {
                    if (applyButton.isExtended)
                        applyButton.shrink()
                    // Scrolling down
                    //   Log.i("makis", " Scrolling down")

                }

            })
        }
    }

    private fun updateRangeFrom(value: Int) {
        if (value == 0) {
            binding.fromRangeRadiusTv.setText(getString(R.string.show_all))
            searchTripFilter.rangeFrom = null
        } else {
            binding.fromRangeRadiusTv.setText(getString(R.string.range_text, value.toString()))
            searchTripFilter.rangeFrom = value
        }
    }

    private fun updateRangeTo(value: Int) {
        if (value == 0) {
            binding.toRangeRadiusTv.setText(getString(R.string.show_all))
            searchTripFilter.rangeTo = null
        } else {
            binding.toRangeRadiusTv.setText(getString(R.string.range_text, value.toString()))
            searchTripFilter.rangeTo = value
        }
    }

    private fun updateRangePrice(value: Int) {
        if (value == 0) {
            binding.priceTv.setText(getString(R.string.show_all))
            searchTripFilter.priceMax = null
        } else {
            binding.priceTv.setText(getString(R.string.range_text_price, value.toString()))
            searchTripFilter.priceMax = value
        }
    }


    private fun applyChanges() {
        val resultIntent = Intent()
        resultIntent.putExtra("filter", searchTripFilter)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun initializePet() {
        if (searchTripFilter.pet == true) {
            binding.pet.setText(resources.getString(R.string.yes))
        } else if (searchTripFilter.pet == false) {
            binding.pet.setText(resources.getString(R.string.no))
        }
    }

    private fun onPetItemClickListener(petAnswerType: PetAnswerType) {

        when (petAnswerType) {
            PetAnswerType.Yes -> {
                searchTripFilter.pet = true
                binding.pet.setText(resources.getString(R.string.yes))
            }
            PetAnswerType.No -> {
                searchTripFilter.pet = false
                binding.pet.setText(resources.getString(R.string.no))
            }
            else -> {
                searchTripFilter.pet = null
                binding.pet.text = null
            }
        }
    }

}
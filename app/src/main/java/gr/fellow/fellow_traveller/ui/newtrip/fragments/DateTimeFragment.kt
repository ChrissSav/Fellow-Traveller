package gr.fellow.fellow_traveller.ui.newtrip.fragments

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentDateTimeBinding
import gr.fellow.fellow_traveller.ui.dialogs.DatePickerCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.TimePickerCustomDialog
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.utils.validateDateTimeDiffer


@AndroidEntryPoint
class DateTimeFragment : BaseFragment<FragmentDateTimeBinding>() {

    private val viewModel: NewTripViewModel by activityViewModels()
    private lateinit var dateDialog: DatePickerCustomDialog
    private lateinit var timeDialog: TimePickerCustomDialog


    override fun getViewBinding(): FragmentDateTimeBinding =
        FragmentDateTimeBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.date.observe(viewLifecycleOwner, Observer {
            binding.fellowTextInputDate.text = it
        })

        viewModel.time.observe(viewLifecycleOwner, Observer {
            binding.fellowTextInputTime.text = it
        })

    }

    override fun setUpViews() {
        binding.fellowTextInputDate.onClickListener {
            dateDialog = DatePickerCustomDialog(
                binding.fellowTextInputDate.text.toString()
            ) {

                viewModel.applyDate(it)
            }
            dateDialog.show(childFragmentManager, "dateDialog")

        }

        binding.fellowTextInputTime.onClickListener {
            timeDialog = TimePickerCustomDialog(
                binding.fellowTextInputTime.text.toString()
            ) {
                viewModel.applyTime(it)
            }
            timeDialog.show(childFragmentManager, "dateDialog")

        }


        binding.ImageButtonNext.root.setOnClickListener {
            if (validateDateTimeDiffer(
                    viewModel.date.value.toString(), viewModel.time.value.toString(), resources.getInteger(R.integer.Time_difference)
                )
            ) {
                findNavController()?.navigate(R.id.next_fragment)
            } else {
                viewModel.setError(R.string.ERROR_TRIP_TIMESTAMP)
            }
        }
    }

}
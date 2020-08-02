package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentDateTimeBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.dialogs.DatePickerCustomDialog
import gr.fellow.fellow_traveller.ui.dialogs.TimePickerCustomDialog
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.utils.validateDateTimeDiffer


class DateTimeFragment : Fragment() {

    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var dateDialog: DatePickerCustomDialog
    private lateinit var timeDialog: TimePickerCustomDialog

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentDateTimeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDateTimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        if (newTripViewModel.date.value != null) {
            binding.dateButton.text = newTripViewModel.date.value.toString()
            binding.dateButton.setTextColor(resources.getColor(R.color.button_fill_color))
        }
        if (newTripViewModel.time.value != null) {
            binding.timeButton.text = newTripViewModel.time.value.toString()
            binding.timeButton.setTextColor(resources.getColor(R.color.button_fill_color))
        }

        binding.dateButton.setOnClickListener {
            dateDialog = DatePickerCustomDialog(
                binding.dateButton.text.toString(),
                resources.getString(R.string.set_date)
            ) {

                newTripViewModel.applyDate(it)
            }
                fragmentManager?.let { it1 -> dateDialog.show(it1, "dateDialog") }

        }

        binding.timeButton.setOnClickListener {
            timeDialog = TimePickerCustomDialog(
                binding.timeButton.text.toString(),
                resources.getString(R.string.set_time)
            ) {
                newTripViewModel.applyTime(it)
            }
            fragmentManager?.let { it1 -> timeDialog.show(it1, "dateDialog") }

        }

        newTripViewModel.date.observe(viewLifecycleOwner, Observer {
            binding.dateButton.text = it
            binding.dateButton.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        newTripViewModel.time.observe(viewLifecycleOwner, Observer {
            binding.timeButton.text = it
            binding.timeButton.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        binding.ImageButtonNext.setOnClickListener {
            if (validateDateTimeDiffer(
                    newTripViewModel.date.value.toString(),
                    newTripViewModel.time.value.toString(),
                    resources.getInteger(R.integer.Time_difference)
                )
            ) {
                navController.navigate(R.id.next_fragment)
            } else {
                createSnackBar(view, resources.getString(R.string.ERROR_TRIP_TIMESTAMP))
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
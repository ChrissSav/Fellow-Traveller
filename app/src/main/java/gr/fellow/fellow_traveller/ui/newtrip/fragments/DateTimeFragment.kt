package gr.fellow.fellow_traveller.ui.newtrip.fragments

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
import gr.fellow.fellow_traveller.databinding.FragmentDateTimeBinding
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDateTimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        newTripViewModel.date.observe(viewLifecycleOwner, Observer {
            binding.dateButton.setText(it)
        })

        newTripViewModel.time.observe(viewLifecycleOwner, Observer {
            binding.timeButton.setText(it)
        })

        binding.dateButton.setOnClickListener {
            dateDialog = DatePickerCustomDialog(
                binding.dateButton.text.toString()
            ) {

                newTripViewModel.applyDate(it)
            }
            dateDialog.show(childFragmentManager, "dateDialog")

        }

        binding.timeButton.setOnClickListener {
            timeDialog = TimePickerCustomDialog(
                binding.timeButton.text.toString()
            ) {
                newTripViewModel.applyTime(it)
            }
            timeDialog.show(childFragmentManager, "dateDialog")

        }


        binding.ImageButtonNext.setOnClickListener {
            if (validateDateTimeDiffer(
                    newTripViewModel.date.value.toString(), newTripViewModel.time.value.toString(), resources.getInteger(R.integer.Time_difference)
                )
            ) {
                navController.navigate(R.id.next_fragment)
            } else {
                newTripViewModel.setError(R.string.ERROR_TRIP_TIMESTAMP)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
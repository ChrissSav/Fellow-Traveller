package com.example.fellowtravellerbeta.ui.newTrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.dialogs.DatePickerCustomDialog
import com.example.fellowtravellerbeta.ui.dialogs.TimePickerCustomDialog
import com.example.fellowtravellerbeta.ui.newTrip.NewTripViewModel
import com.example.fellowtravellerbeta.utils.createSnackBar
import com.example.fellowtravellerbeta.utils.validateDateTimeDiffer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject


class AddDateTimeFragment : Fragment() {

    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var dateDialog: DatePickerCustomDialog
    private lateinit var timeDialog: TimePickerCustomDialog
    private lateinit var navController: NavController

    private val viewModel: NewTripViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_date_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        dateButton = view.findViewById(R.id.AddDateTimeFragment_button_date)
        timeButton = view.findViewById(R.id.AddDateTimeFragment_button_time)
        nextButton = view.findViewById(R.id.AddDateTimeFragment_button_next)
        backButton = view.findViewById(R.id.imageButton_back)


        if (viewModel.date.value != null) {
            dateButton.text = viewModel.date.value.toString()
            dateButton.setTextColor(resources.getColor(R.color.button_fill_color))
        }
        if (viewModel.time.value != null) {
            timeButton.text = viewModel.time.value.toString()
            timeButton.setTextColor(resources.getColor(R.color.button_fill_color))
        }




        timeButton.setOnClickListener {
            timeDialog = TimePickerCustomDialog(
                timeButton.text.toString(),
                resources.getString(R.string.set_time),
                viewModel
            )
            fragmentManager?.let { it1 -> timeDialog.show(it1,"dateDialog") }
        }


        dateButton.setOnClickListener {
            dateDialog = DatePickerCustomDialog(
                dateButton.text.toString(),
                resources.getString(R.string.set_date),
                viewModel
            )
            fragmentManager?.let { it1 -> dateDialog.show(it1,"dateDialog") }
        }


        viewModel.date.observe(viewLifecycleOwner, Observer {
            dateButton.text = it
            dateButton.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        viewModel.time.observe(viewLifecycleOwner, Observer {
            timeButton.text = it
            timeButton.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        nextButton.setOnClickListener {
            if(validateDateTimeDiffer( viewModel.date.value.toString(),  viewModel.time.value.toString(), resources.getInteger(R.integer.Time_difference))){
                navController.navigate(R.id.action_addDateTimeFragment_to_addBaseInfoFragment)
            }else{
                createSnackBar(view,resources.getString(R.string.ERROR_TRIP_TIMESTAMP))
            }
        }
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }




}
package com.example.fellowtravellerbeta.ui.newTrip.fragments

import android.content.Intent
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
import com.example.fellowtravellerbeta.ui.newTrip.NewTripViewModel
import com.example.fellowtravellerbeta.ui.newTrip.SelectLocationActivity
import com.example.fellowtravellerbeta.utils.createSnackBar
import org.koin.android.ext.android.inject


class AddLocationsFragment : Fragment() {

    private lateinit var buttonFrom: Button
    private lateinit var buttonTo: Button
    private lateinit var buttonNext: ImageButton
    private val viewModel: NewTripViewModel by inject()
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonFrom = view.findViewById(R.id.AddLocationsFragment_button_from)
        buttonTo = view.findViewById(R.id.AddLocationsFragment_button_to)
        buttonNext = view.findViewById(R.id.AddLocationsFragment_button_next)

        navController = Navigation.findNavController(view)

        if (viewModel.destinationFrom.value != null) {
            buttonFrom.text = viewModel.destinationFrom.value?.title
            buttonFrom.setTextColor(resources.getColor(R.color.button_fill_color))
        }
        if (viewModel.destinationTo.value != null) {
            buttonTo.text = viewModel.destinationTo.value?.title
            buttonTo.setTextColor(resources.getColor(R.color.button_fill_color))
        }

        buttonTo.setOnClickListener {
            val intent = Intent(activity, SelectLocationActivity::class.java)
            intent.putExtra("destination","to")
            startActivityForResult(intent, 1)
        }
        buttonFrom.setOnClickListener {
            val intent = Intent(activity, SelectLocationActivity::class.java)
            intent.putExtra("destination","from")
            startActivityForResult(intent, 1)
        }


        viewModel.destinationFrom.observe(viewLifecycleOwner, Observer {
            buttonFrom.text = it.title
            buttonFrom.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        viewModel.destinationTo.observe(viewLifecycleOwner, Observer {
            buttonTo.text = it.title
            buttonTo.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        buttonNext.setOnClickListener {

            if (viewModel.destinationFrom.value == null) {
                createSnackBar(view, "Παρακαλώ επιλέξτε σημείο αφετηρίας")
            }
            else if (viewModel.destinationTo.value == null) {
                createSnackBar(view, "Παρακαλώ επιλέξτε σημείο προορισμού")
            }else{
                navController.navigate(R.id.action_addLocationsFragment_to_addDateTimeFragment)
            }

        }


    }


}
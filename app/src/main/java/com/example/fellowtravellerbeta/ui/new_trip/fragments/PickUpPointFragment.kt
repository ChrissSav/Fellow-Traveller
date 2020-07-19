package com.example.fellowtravellerbeta.ui.new_trip.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.new_trip.NewTripViewModel
import com.example.fellowtravellerbeta.ui.new_trip.SelectLocationActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.lifecycle.Observer
import com.example.fellowtravellerbeta.utils.createSnackBar


class PickUpPointFragment : Fragment() {

    private lateinit var buttonPickUp: Button
    private lateinit var buttonNext: ImageButton
    private val viewModel: NewTripViewModel by sharedViewModel()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_up_point, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        buttonPickUp = view.findViewById(R.id.PickUpPointFragment_button_pickup)

        buttonNext = view.findViewById(R.id.PickUpPointFragment_button_next)


        if (viewModel.destinationPickUp.value != null) {
            buttonPickUp.text = viewModel.destinationPickUp.value?.title
            buttonPickUp.setTextColor(resources.getColor(R.color.button_fill_color))
        }

        buttonPickUp.setOnClickListener {
            val intent = Intent(activity, SelectLocationActivity::class.java)
            startActivityForResult(intent, 1)
        }

        buttonNext.setOnClickListener {
            if(viewModel.destinationPickUp.value == null){
                createSnackBar(view, "Παρακαλώ επιλέξτε σημείο αναχώρησεις")

            }else{
                navController.navigate(R.id.action_pickUpPointFragment_to_addDateTimeFragment)
            }
        }


        viewModel.destinationPickUp.observe(viewLifecycleOwner, Observer {
            buttonPickUp.text = it.title
            buttonPickUp.setTextColor(resources.getColor(R.color.button_fill_color))

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                viewModel.setDestinationPickUp(id, title)
            }
        }
    }

}
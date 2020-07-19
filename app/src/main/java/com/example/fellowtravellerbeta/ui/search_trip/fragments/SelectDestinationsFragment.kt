package com.example.fellowtravellerbeta.ui.search_trip.fragments

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
import com.example.fellowtravellerbeta.ui.search_trip.SearchTripViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SelectDestinationsFragment : Fragment() {

    private lateinit var imageButtonFrom: ImageButton
    private lateinit var imageButtonTo: ImageButton
    private lateinit var imageButtonBack: ImageButton
    private lateinit var buttonNext: Button
    private lateinit var nav: NavController
    private val viewModel: SearchTripViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_select_destinations, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nav = Navigation.findNavController(view)

        imageButtonFrom = view.findViewById(R.id.SelectDestinationsFragment_destFrom_button)
        imageButtonTo = view.findViewById(R.id.SelectDestinationsFragment_destΤο_button)
        buttonNext = view.findViewById(R.id.SelectDestinationsFragment_search_button)
        imageButtonBack = view.findViewById(R.id.SelectDestinationsFragment_back_button)

        buttonNext.setOnClickListener {
            nav.navigate(R.id.action_selectDestinationsFragment_to_resultsFragment)
        }

        imageButtonBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}
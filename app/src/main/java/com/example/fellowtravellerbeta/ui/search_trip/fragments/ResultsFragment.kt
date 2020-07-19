package com.example.fellowtravellerbeta.ui.search_trip.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.search_trip.SearchTripViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ResultsFragment : Fragment() {
    private val viewModel: SearchTripViewModel by sharedViewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }


}
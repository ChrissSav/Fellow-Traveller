package com.example.fellowtravellerbeta.ui.new_trip.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R


class BaseInfoFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var nextButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        nextButton = view.findViewById(R.id.AddBaseInfoFragment_button_next)
        nextButton.setOnClickListener {
            navController.navigate(R.id.action_addBaseInfoFragment_to_addPriceFragment)
        }

    }

}


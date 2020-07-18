package com.example.fellowtravellerbeta.ui.newTrip.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.newTrip.NewTripViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PriceFragment : Fragment() {
    private val viewModel: NewTripViewModel by sharedViewModel()
    private lateinit var navController: NavController
    private lateinit var nextButton: ImageButton
    private lateinit var priceText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        nextButton = view.findViewById(R.id.AddPriceFragment_button_next)
        priceText = view.findViewById(R.id.AddPriceFragment_editText_price)


        viewModel.price.value?.let {
            priceText.setText(viewModel.price.value.toString())
        }


        nextButton.setOnClickListener {
            val price = if (priceText.text.isEmpty()) "0" else priceText.text.toString()
            viewModel.setPrice(price)
        }

        viewModel.price.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.navController)
        })
    }
}
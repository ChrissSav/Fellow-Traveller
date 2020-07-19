package com.example.fellowtravellerbeta.ui.search_trip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.new_trip.NewTripViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchTripActivity : AppCompatActivity() {

    private val viewModel: SearchTripViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_trip)
    }
}
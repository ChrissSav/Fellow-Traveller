package com.example.fellowtravellerbeta.di


import com.example.fellowtravellerbeta.ui.new_trip.NewTripViewModel
import com.example.fellowtravellerbeta.ui.register.RegisterSharedViewModel
import com.example.fellowtravellerbeta.ui.search_trip.SearchTripViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel  { RegisterSharedViewModel(get()) }
    viewModel  { NewTripViewModel(get()) }
    viewModel  { SearchTripViewModel(get()) }

}
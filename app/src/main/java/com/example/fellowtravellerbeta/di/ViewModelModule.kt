package com.example.fellowtravellerbeta.di


import com.example.fellowtravellerbeta.ui.newTrip.NewTripViewModel
import com.example.fellowtravellerbeta.ui.register.RegisterSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel  { RegisterSharedViewModel(get()) }
    viewModel  { NewTripViewModel(get()) }

}
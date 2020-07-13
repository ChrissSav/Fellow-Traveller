package com.example.fellowtravellerbeta.di


import com.example.fellowtravellerbeta.ui.newTrip.NewTripViewModel
import com.example.fellowtravellerbeta.ui.register.RegisterSharedViewModel
import org.koin.dsl.module

val viewModels = module {
    single  { RegisterSharedViewModel(get()) }
    single  { NewTripViewModel(get()) }

}
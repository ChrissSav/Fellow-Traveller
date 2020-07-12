package com.example.fellowtravellerbeta.di


import com.example.fellowtravellerbeta.ui.register.fragment.RegisterSharedViewModel
import org.koin.dsl.module

val viewModels = module {
    single  { RegisterSharedViewModel(get()) }
}
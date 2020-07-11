package com.example.fellowtravellerbeta.di


import com.example.fellowtravellerbeta.ui.register.fragments.accountInfo.AccountInfoViewModel
import com.example.fellowtravellerbeta.ui.register.fragments.phone.SetPhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { AccountInfoViewModel(get()) }
    viewModel { SetPhoneViewModel(get()) }

}
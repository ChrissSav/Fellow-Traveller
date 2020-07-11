package com.example.fellowtravellerbeta

import android.app.Application
import com.example.fellowtravellerbeta.di.networkModule
import com.example.fellowtravellerbeta.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FellowTraveller : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FellowTraveller)
            modules(
                networkModule,
                viewModels
            )
        }

    }
}
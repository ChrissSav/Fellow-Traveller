package com.example.fellowtravellerbeta.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.register.fragment.RegisterSharedViewModel
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private val registerSharedViewModel: RegisterSharedViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerSharedViewModel.clearAll()
        setContentView(R.layout.activity_register)


    }


}
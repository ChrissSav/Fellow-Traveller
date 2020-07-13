package com.example.fellowtravellerbeta.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fellowtravellerbeta.R
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private val registerSharedViewModel: RegisterSharedViewModel by inject()
    private lateinit var nav: NavController
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        registerSharedViewModel.clearAll()
        nav = Navigation.findNavController(this, R.id.RegisterActivity_nav_host)
        progressBar = findViewById(R.id.RegisterActivity_progressBar)
        nav.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.setPhoneFragment -> progressBar.progress = 25
                R.id.setEmailFragment -> progressBar.progress = 50
                R.id.setPasswordFragment -> progressBar.progress = 75
                R.id.accountInfoFragment -> progressBar.progress = 90
            }

        })

    }


}
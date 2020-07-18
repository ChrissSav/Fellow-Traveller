package com.example.fellowtravellerbeta.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.newTrip.NewTripActivity
import com.example.fellowtravellerbeta.ui.register.RegisterActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var registerButton: Button

    private lateinit var registerTripButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        registerButton = findViewById(R.id.MainActivity_button_register)
        registerTripButton  = findViewById(R.id.MainActivity_button_login)
        registerButton.setOnClickListener {
            val mainIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mainIntent)
        }
        registerTripButton.setOnClickListener {
            val mainIntent = Intent(this, NewTripActivity::class.java)
            startActivity(mainIntent)
        }
    }
}
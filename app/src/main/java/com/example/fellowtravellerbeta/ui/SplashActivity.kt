package com.example.fellowtravellerbeta.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.welcome.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
const val TIME = 1500

class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()

        }, TIME.toLong())

    }
}
package com.example.fellowtravellerbeta.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.ui.register.RegisterActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)














        Handler().postDelayed({
            val mainIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mainIntent)
            finish()

        }, SPLASH_TIME.toLong())

    }
}
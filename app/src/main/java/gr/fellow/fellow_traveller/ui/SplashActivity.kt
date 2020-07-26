package gr.fellow.fellow_traveller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.ui.home.main.HomeActivity

const val TIME = 1500

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            val mainIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainIntent)
            finish()

        }, TIME.toLong())
    }
}
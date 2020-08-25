package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivitySplashBinding
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        Handler().postDelayed({
            val intent = if (sharedPreferences.getString(PREFS_AUTH_TOKEN, "").toString().length > 10) {
                Intent(this, HomeActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 500)

        /*splashViewModel.result.observe(this, Observer {
            val intent = if (it)
                Intent(this, HomeActivity::class.java)
            else
                Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        })*/
    }
}
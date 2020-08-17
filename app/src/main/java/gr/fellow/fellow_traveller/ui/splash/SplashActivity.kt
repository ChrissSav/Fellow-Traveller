package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivitySplashBinding
import gr.fellow.fellow_traveller.di.Fellow
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity
import okhttp3.OkHttpClient
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Fellow
    @Inject
    lateinit var okHttpClient: OkHttpClient.Builder

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        Handler().postDelayed({
            splashViewModel.checkUserState()
        }, 750)

        splashViewModel.result.observe(this, Observer {
            val intent = if (it)
                Intent(this, HomeActivity::class.java)
            else
                Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        })
    }
}
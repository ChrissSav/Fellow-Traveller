package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.ui.home.main.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.ui.register.RegisterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            splashViewModel.checkUserState()
        }, 750)

        splashViewModel.result.observe(this, Observer {
            val intent = if(it){
                Intent(this, HomeActivity::class.java)
            }else{
                Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        })
    }
}
package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.constraintlayout.motion.widget.MotionLayout
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySplashBinding
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.ui.openActivityWithFade
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun provideViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        /*splashViewModel.result.observe(this, Observer {
             val intent = if (it)
                 Intent(this, HomeActivity::class.java)
             else
                 Intent(this, MainActivity::class.java)

             startActivity(intent)
             finish()
         })*/
    }

    override fun setUpViews() {
        val intent = if (sharedPreferences.getString(PREFS_AUTH_TOKEN, "").toString().isNotEmpty()) {
            Intent(this@SplashActivity, HomeActivity::class.java)
        } else {
            Intent(this@SplashActivity, MainActivity::class.java)
        }


        binding.motion.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                openActivityWithFade(intent)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
            }
        })
    }


}
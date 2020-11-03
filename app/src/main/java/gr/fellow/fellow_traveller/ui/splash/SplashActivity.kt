package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySplashBinding
import gr.fellow.fellow_traveller.ui.extensions.openActivityWithFade
import gr.fellow.fellow_traveller.ui.extensions.postDelay
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    private var intentOpen: Intent? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun provideViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.userInfo.observe(this, Observer {
            intentOpen = if (it)
                Intent(this, HomeActivity::class.java)
            else
                Intent(this, MainActivity::class.java)
            viewModel.setFist(true)
        })

        viewModel.finish.observe(this, Observer {
            if (it.first && it.second)
                openActivityWithFade(intentOpen!!)
        })

        viewModel.errorSecond.observe(this, Observer {

            intentOpen = if (it.internal && it.messageId == R.string.ERROR_API_UNAUTHORIZED)
                Intent(this, MainActivity::class.java)
            else
                Intent(this, HomeActivity::class.java)
            viewModel.setFist(true)
        })

    }

    override fun setUpViews() {
        postDelay(2000) {
            viewModel.getUserInfo()

        }


        binding.motion.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                viewModel.setSecond(true)
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
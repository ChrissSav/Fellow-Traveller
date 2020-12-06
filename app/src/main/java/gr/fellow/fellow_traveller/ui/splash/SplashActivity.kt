package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.data.base.GifDrawableImageViewTarget
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
            intentOpen = Intent(this, HomeActivity::class.java)
            viewModel.setFist(true)
        })

        viewModel.finish.observe(this, Observer {
            if (it.first && it.second)
                openActivityWithFade(intentOpen!!)
        })

        viewModel.error.observe(this, Observer {
            intentOpen = Intent(this, MainActivity::class.java)
            viewModel.setFist(true)
        })

    }

    override fun setUpViews() {

        viewModel.getUserInfo()

        postDelay(2100) {
            viewModel.setSecond(true)
        }

        Glide.with(this)
            .load(R.raw.splash_green_100fps)
            .into(GifDrawableImageViewTarget(binding.ImageView, 1))
    }
}
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
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_REFRESH_TOKEN
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

        viewModel.error.observe(this, Observer {
            intentOpen = if (it.internal && it.messageId == R.string.ERROR_API_UNAUTHORIZED)
                Intent(this, MainActivity::class.java)
            else if (sharedPreferences.getString(PREFS_AUTH_REFRESH_TOKEN, "").isNullOrEmpty())
                Intent(this, MainActivity::class.java)
            else
                Intent(this, HomeActivity::class.java)
            viewModel.setFist(true)
        })

    }

    override fun setUpViews() {

        viewModel.getUserInfo()


        Glide.with(this)
            .load(R.raw.fellow_logo_gif)
            .into(GifDrawableImageViewTarget(binding.ImageView, 1) {
                viewModel.setSecond(true)
            })
    }


}
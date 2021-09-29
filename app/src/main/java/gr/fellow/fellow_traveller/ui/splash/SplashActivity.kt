package gr.fellow.fellow_traveller.ui.splash

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivitySplashBinding
import gr.fellow.fellow_traveller.ui.extensions.openActivityWithFade
import gr.fellow.fellow_traveller.ui.extensions.postDelay
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.main.MainActivity


@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()
    private var intentOpen: Intent? = null

    override fun provideViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        viewModel.userInfo.observe(this, Observer {
            intentOpen = Intent(this, HomeActivity::class.java)
            viewModel.setFist(true)
        })

        viewModel.finish.observe(this, Observer {
            if (it.first && it.second)
                openActivityWithFade(intentOpen ?: Intent(this@SplashActivity, MainActivity::class.java))
        })

        viewModel.error.observe(this, Observer {
            intentOpen = Intent(this, MainActivity::class.java)
            viewModel.setFist(true)
        })

    }

    override fun setUpViews() {

        viewModel.getUserInfo()

        Glide.with(this).asGif().load(R.raw.fellow_splash_black).listener(object : RequestListener<GifDrawable?> {

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable?>?, isFirstResource: Boolean): Boolean {
                binding.ImageView.setColorFilter(ContextCompat.getColor(this@SplashActivity, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
                binding.ImageView.setImageResource(R.drawable.ic_fellow)
                backUp()
                return true
            }

            override fun onResourceReady(
                resource: GifDrawable?, model: Any?, target: Target<GifDrawable?>?, dataSource: DataSource?, isFirstResource: Boolean,
            ): Boolean {
                resource?.setLoopCount(1)
                resource?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable) {
                        viewModel.setSecond(true)
                    }
                })
                return false
            }
        }).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .into(binding.ImageView)
    }


    private fun backUp() {
        postDelay(2100) {
            viewModel.setSecond(true)
        }
    }
}
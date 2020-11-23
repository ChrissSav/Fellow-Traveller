package gr.fellow.fellow_traveller.ui.home.messenger

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityMessengerLinkBinding
import gr.fellow.fellow_traveller.ui.home.messenger.fragments.*


class MessengerLinkActivity : BaseActivity<ActivityMessengerLinkBinding>() {


    override fun provideViewBinding(): ActivityMessengerLinkBinding =
        ActivityMessengerLinkBinding.inflate(layoutInflater)

    override fun setUpObservers() {


    }

    override fun setUpViews() {

        val fragmentList = arrayListOf<Fragment>(
            MessengerInitialFragment(),
            MessengerStep1Fragment(),
            MessengerStep2Fragment(),
            MessengerStep3Fragment(),
            MessengerStep4Fragment(),
            MessengerStep5Fragment()
        )

        val adapter = ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.viewPager)

        binding.exit.setOnClickListener {
            finish()
        }
    }


    fun sendBackMessengerLink(link: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("messenger", link)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


}
package gr.fellow.fellow_traveller.ui.home.messenger

import androidx.fragment.app.Fragment
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityMessengerLinkBinding
import gr.fellow.fellow_traveller.ui.home.messenger.fragments.MessengerInitialFragment
import gr.fellow.fellow_traveller.ui.home.messenger.fragments.MessengerStep1Fragment
import gr.fellow.fellow_traveller.ui.home.messenger.fragments.MessengerStep2Fragment
import gr.fellow.fellow_traveller.ui.home.messenger.fragments.MessengerStep3Fragment


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
            MessengerStep3Fragment()
        )

        val adapter = ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.viewPager)

        binding.exit.setOnClickListener {
            finish()
        }
    }


}
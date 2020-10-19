package gr.fellow.fellow_traveller.ui.home

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityHomeBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.navigateWithFade
import gr.fellow.fellow_traveller.ui.extensions.toPx


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController


    private val homeLayout = listOf(
        R.id.destination_main,
        R.id.destination_trips_offers,
        R.id.destination_trips_takes_part,
        R.id.destination_notifications,
        R.id.destination_info
    )



    override fun provideViewBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.user.observe(this, Observer {
            if (it.messengerLink == null) {
                binding.constraintLayoutMessenger.visibility = View.VISIBLE
            } else {
                binding.constraintLayoutMessenger.visibility = View.GONE

            }
        })


        viewModel.errorSecond.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId))
            else
                createAlerter(it.message)
        })
        viewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })
    }

    override fun setUpViews() {


        viewModel.loadUserInfo()
        viewModel.loadCars()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)


        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id in homeLayout) {
                showHideBottomNav(55.toPx)
            } else {
                showHideBottomNav(0)
            }

            if (destination.id == R.id.accountSettingsFragment)
                binding.constraintLayoutMessenger.visibility = View.GONE
            else if (viewModel.user.value?.messengerLink == null)
                binding.constraintLayoutMessenger.visibility = View.VISIBLE

        })

        binding.toAccountInfo.setOnClickListener {
            navController.navigateWithFade(R.id.accountSettingsFragment)
        }
    }


    private fun showHideBottomNav(targetHeight: Int) {
        val slideAnimator = ValueAnimator
            .ofInt(binding.HomeActivityBottomNavigationView.layoutParams.height, targetHeight)
            .setDuration(200)
        slideAnimator.addUpdateListener { animation1: ValueAnimator ->
            val value = animation1.animatedValue as Int
            binding.HomeActivityBottomNavigationView.layoutParams.height = value
            binding.HomeActivityBottomNavigationView.requestLayout()
        }
        val animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.start()
    }


    private fun setupBottomNavMenu(navController: NavController) {
        binding.HomeActivityBottomNavigationView.let {
            NavigationUI.setupWithNavController(it, navController)
            it.setOnNavigationItemReselectedListener { item ->
                if (item.isChecked) {
                    return@setOnNavigationItemReselectedListener
                }
            }
        }
    }

}
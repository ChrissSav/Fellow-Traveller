package gr.fellow.fellow_traveller.ui.home

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.util.DisplayMetrics
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
import gr.fellow.fellow_traveller.ui.createAlerter


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


    override fun onRestart() {
        viewModel.loadCars()
        super.onRestart()
    }


    override fun provideViewBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.loadUserInfo()
        viewModel.loadCars()

        viewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })
    }

    override fun setUpViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)


        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id in homeLayout) {
                showHideBottomNav(convertDpToPixel(55))
            } else {
                showHideBottomNav(0)
            }

        })
    }


    private fun convertDpToPixel(dp: Int): Int {
        return (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
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
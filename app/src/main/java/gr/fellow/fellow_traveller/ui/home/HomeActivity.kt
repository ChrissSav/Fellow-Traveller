package gr.fellow.fellow_traveller.ui.home

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityHomeBinding
import gr.fellow.fellow_traveller.ui.createSnackBar


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding

    private val homeLayout = listOf(
        R.id.destination_main,
        R.id.destination_trips,
        R.id.destination_messages,
        R.id.destination_notifications,
        R.id.destination_info
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)


        homeViewModel.loadUserInfo()
        homeViewModel.loadCars()
        homeViewModel.loadTripAsCreator()

        homeViewModel.error.observe(this, Observer {
            createSnackBar(binding.root, it)
        })

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id in homeLayout) {
                showHideBottomNav(convertDpToPixel(55))
            } else {
                showHideBottomNav(0)
            }

        })
    }

    override fun onRestart() {
        homeViewModel.loadCars()
        super.onRestart()
    }


    private fun convertDpToPixel(dp: Int): Int {
        return (dp * (resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
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
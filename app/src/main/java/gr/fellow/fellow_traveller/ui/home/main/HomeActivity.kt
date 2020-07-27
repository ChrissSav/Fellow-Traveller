package gr.fellow.fellow_traveller.ui.home.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)

        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.HomeActivityBottomNavigationView.let {
            NavigationUI.setupWithNavController(it, navController)
            it.setOnNavigationItemReselectedListener { item->
                if (item.isChecked) {
                    return@setOnNavigationItemReselectedListener
                }
            }


        }
    }


}
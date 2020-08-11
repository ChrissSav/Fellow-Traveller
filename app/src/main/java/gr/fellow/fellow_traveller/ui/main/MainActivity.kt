package gr.fellow.fellow_traveller.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivityMainBinding
import gr.fellow.fellow_traveller.ui.createSnackBar


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.load.observe(this, Observer {
           if(it)
               binding.progressLoad.visibility = View.VISIBLE
            else
               binding.progressLoad.visibility = View.INVISIBLE

        })



        loginViewModel.error.observe(this, Observer {
            createSnackBar(binding.root, it)
        })


    }
}
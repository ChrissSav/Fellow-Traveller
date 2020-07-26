package gr.fellow.fellow_traveller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gr.fellow.fellow_traveller.databinding.ActivityMainBinding
import gr.fellow.fellow_traveller.databinding.ActivityRegisterBinding
import gr.fellow.fellow_traveller.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.buttonRegister.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
}
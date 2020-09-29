package gr.fellow.fellow_traveller.ui.car

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.ActivityAddCarBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.utils.isValidPlate

@AndroidEntryPoint
class AddCarActivity : AppCompatActivity() {

    private val addCarViewModel: AddCarViewModel by viewModels()
    private lateinit var binding: ActivityAddCarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        binding.addCar.setOnClickListener {
            val brand = binding.brand.text.toString()
            val model = binding.model.text.toString()
            val plate = binding.plate.text.toString()
            val color = binding.color.text.toString()

            if (checkErrors()) {
                addCarViewModel.addCar(brand, model, plate, color)
            }
        }

        addCarViewModel.error.observe(this, Observer {
            createAlerter(getString(it), R.color.aqua)
        })

        addCarViewModel.saved.observe(this, Observer {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    private fun checkErrors(): Boolean {
        if (binding.brand.text.toString().length < 2 || binding.model.text.toString().length < 2 || binding.color.text.toString().length < 2) {
            addCarViewModel.setError(R.string.ERROR_FIELDS_REQUIRE)
            return false
        }
        if (!isValidPlate(binding.plate.text.toString())) {
            addCarViewModel.setError(R.string.ERROR_INVALID_PLATE_FORMAT)
            return false
        }
        return true
    }
}
package gr.fellow.fellow_traveller.ui.car

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.databinding.ActivityAddCarBinding
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.utils.isValidPlate

@AndroidEntryPoint
class AddCarActivity : AppCompatActivity() {

    private val addCarViewModel: AddCarViewModel by viewModels()
    private lateinit var binding: ActivityAddCarBinding
    private var textErrors = mutableListOf<TextView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        textErrors.add(binding.textViewBrandError)
        textErrors.add(binding.textViewModelError)
        textErrors.add(binding.textViewBrandError)
        textErrors.add(binding.textViewColorError)
        textErrors.add(binding.textViewPlateError)

        binding.closeButtonAddCarSettings.setOnClickListener {
            finish()
        }


        binding.EditTextBrand.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().length > 2)
                    binding.textViewBrandError.visibility = View.INVISIBLE
                else
                    binding.textViewBrandError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.EditTextModel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().length > 2)
                    binding.textViewModelError.visibility = View.INVISIBLE
                else
                    binding.textViewModelError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.EditTextPlate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (isValidPlate(charSequence.toString()))
                    binding.textViewPlateError.visibility = View.INVISIBLE
                else
                    binding.textViewPlateError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        binding.EditTextColor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(charSequence: Editable?) {
                if (charSequence.toString().length > 2)
                    binding.textViewColorError.visibility = View.INVISIBLE
                else
                    binding.textViewColorError.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.buttonAdd.setOnClickListener {
            val brand = binding.EditTextBrand.text.toString()
            val model = binding.EditTextModel.text.toString()
            val plate = binding.EditTextPlate.text.toString()
            val color = binding.EditTextColor.text.toString()

            if (checkErrors()) {
                addCarViewModel.addCar(brand, model, plate, color)
            }

        }

        addCarViewModel.error.observe(this, Observer {
            createAlerter(getString(it))
        })


        addCarViewModel.saved.observe(this, Observer {
            val brand = binding.EditTextBrand.text.toString()
            val model = binding.EditTextModel.text.toString()
            val plate = binding.EditTextPlate.text.toString()
            val color = binding.EditTextColor.text.toString()

            val intent = Intent()
            intent.putExtra("brand", brand)
            intent.putExtra("model", model)
            intent.putExtra("plate", plate)
            intent.putExtra("color", color)
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    private fun checkErrors(): Boolean {
        for (error in textErrors)
            if (error.visibility == View.VISIBLE)
                return false
        return true
    }
}
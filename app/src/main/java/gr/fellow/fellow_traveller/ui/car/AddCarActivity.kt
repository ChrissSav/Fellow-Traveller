package gr.fellow.fellow_traveller.ui.car

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivity
import gr.fellow.fellow_traveller.databinding.ActivityAddCarBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.utils.isValidPlate

@AndroidEntryPoint
class AddCarActivity : BaseActivity<ActivityAddCarBinding>() {

    private val viewModel: AddCarViewModel by viewModels()

    override fun provideViewBinding(): ActivityAddCarBinding =
        ActivityAddCarBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.errorSecond.observe(this, Observer {
            if (it.internal)
                createAlerter(getString(it.messageId), R.color.aqua)
            else
                createAlerter(it.message, R.color.aqua)
        })

        viewModel.saved.observe(this, Observer {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        })

        viewModel.load.observe(this, Observer {
            if (it)
                binding.genericLoader.progressLoad.visibility = View.VISIBLE
            else
                binding.genericLoader.progressLoad.visibility = View.INVISIBLE

        })
    }

    override fun setUpViews() {
        with(binding) {

            back.setOnClickListener {
                finish()
            }

            addCar.setOnClickListener {
                val brand = binding.brand.text.toString()
                val model = binding.model.text.toString()
                val plate = binding.plate.text.toString()
                val color = binding.color.text.toString()

                if (checkErrors()) {
                    viewModel.addCar(brand, model, plate, color)
                }
            }
        }
    }


    private fun checkErrors(): Boolean {
        if (binding.brand.text.toString().length < 2 || binding.model.text.toString().length < 2 || binding.color.text.toString().length < 2) {
            viewModel.setSecondError(R.string.ERROR_FIELDS_REQUIRE)
            return false
        }
        if (!isValidPlate(binding.plate.text.toString())) {
            viewModel.setSecondError(R.string.ERROR_INVALID_PLATE_FORMAT)
            return false
        }
        return true
    }


}
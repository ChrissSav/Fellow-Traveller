package gr.fellow.fellow_traveller.ui.car

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityAddCarBinding
import gr.fellow.fellow_traveller.ui.extensions.createAlerter

@AndroidEntryPoint
class AddCarActivity : BaseActivityViewModel<ActivityAddCarBinding, AddCarViewModel>(AddCarViewModel::class.java) {


    override fun provideViewBinding(): ActivityAddCarBinding =
        ActivityAddCarBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.error.observe(this, Observer {
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

                if (brand.isCorrect() and model.isCorrect() and plate.isCorrect() and color.isCorrect()) {
                    val brandT = brand.text.toString()
                    val modelT = model.text.toString()
                    val plateT = plate.text.toString()
                    val colorT = color.text.toString()
                    viewModel.addCar(brandT, modelT, plateT, colorT)
                }
            }
        }
    }




}
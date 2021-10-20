package gr.fellow.fellow_traveller.ui.car

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseActivityViewModel
import gr.fellow.fellow_traveller.databinding.ActivityAddCarBinding
import gr.fellow.fellow_traveller.domain.car.CarColor
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.CarColorPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.onClickListener
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class AddCarActivity : BaseActivityViewModel<ActivityAddCarBinding, AddCarViewModel>(AddCarViewModel::class.java) {

    private lateinit var carColorPickBottomSheetDialog: CarColorPickBottomSheetDialog

    override fun provideViewBinding(): ActivityAddCarBinding =
        ActivityAddCarBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        viewModel.error.observe(this, {
            if (it.internal)
                createAlerter(getString(it.messageId), R.color.aqua)
            else
                createAlerter(it.message, R.color.aqua)
        })

        viewModel.saved.observe(this, {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        })


        viewModel.color.observe(this, {
            binding.color.text = it.title
            binding.colorView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(it.hex))

            if (Color.parseColor(it.hex) == Color.parseColor("#000000")) {
                binding.whiteStroke.visibility = View.VISIBLE
            } else {
                binding.whiteStroke.visibility = View.GONE
            }
        })


        viewModel.colors.observe(this, {
            carColorPickBottomSheetDialog = CarColorPickBottomSheetDialog(it, this@AddCarActivity::onCarItemClickListener)
        })

        viewModel.load.observe(this, {
            if (it)
                binding.genericLoader.root.visibility = View.VISIBLE
            else
                binding.genericLoader.root.visibility = View.INVISIBLE

        })
    }

    override fun setUpViews() {

        viewModel.getColors()

        with(binding) {


            color.onClickListener {
                carColorPickBottomSheetDialog.show(supportFragmentManager, "carColorPickBottomSheetDialog")
            }

            back.button.setOnClickListener {
                finish()
            }

            addCar.setOnClickListener {

                if (brand.isCorrect() and model.isCorrect() and plate.isCorrect() and color.isCorrect()) {
                    val brandT = brand.text.toString()
                    val modelT = model.text.toString()
                    val plateT = plate.text.toString()
                    viewModel.addCar(brandT, modelT, plateT)
                }
            }
        }
    }


    private fun onCarItemClickListener(carColor: CarColor) {
        viewModel.setColor(carColor)
        carColorPickBottomSheetDialog.dismiss()
    }


}
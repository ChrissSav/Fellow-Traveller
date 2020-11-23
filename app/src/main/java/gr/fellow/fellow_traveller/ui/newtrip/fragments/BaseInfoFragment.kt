package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.content.Intent
import android.widget.CompoundButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBaseInfoBinding
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.BagsStatusPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.CarPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener


class BaseInfoFragment : BaseFragment<FragmentBaseInfoBinding>() {

    private val viewModel: NewTripViewModel by activityViewModels()
    private var carList = mutableListOf<Car>()

    private lateinit var carPickBottomSheetDialog: CarPickBottomSheetDialog
    private lateinit var bagsStatusPickBottomSheetDialog: BagsStatusPickBottomSheetDialog

    override fun getViewBinding(): FragmentBaseInfoBinding =
        FragmentBaseInfoBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        with(viewModel) {
            car.observe(viewLifecycleOwner, Observer {
                binding.carEdiText.setText(it.fullInfo)
            })

            seats.observe(viewLifecycleOwner, Observer {
                binding.seatsPickButton.currentNum = it
            })

            bags.observe(viewLifecycleOwner, Observer {
                binding.bagsPickButton.setText(it.value)
            })

            pet.observe(viewLifecycleOwner, Observer {
                binding.pet.isChecked = it
            })

            carList.observe(viewLifecycleOwner, Observer {
                this@BaseInfoFragment.carList.clear()
                this@BaseInfoFragment.carList.addAll(it)
            })

        }
    }

    override fun setUpViews() {
        viewModel.loadUserCars()

        with(binding) {

            ImageButtonNext.button.setOnClickListener {
                if (checkFields()) {
                    findNavController()?.navigate(R.id.next_fragment)
                }
            }

            bagsPickButton.setOnClickListener {
                bagsStatusPickBottomSheetDialog = BagsStatusPickBottomSheetDialog(this@BaseInfoFragment::onBagsItemClickListener)
                bagsStatusPickBottomSheetDialog.show(childFragmentManager, "bagsStatusPickBottomSheetDialog")
            }


            seatsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    viewModel.setSeats(value)
                }
            }


            pet.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, b ->
                viewModel.setPet(b)
            })


            carEdiText.setOnClickListener {
                carPickBottomSheetDialog = CarPickBottomSheetDialog(carList, this@BaseInfoFragment::onCarItemClickListener)
                carPickBottomSheetDialog.show(childFragmentManager, "carPickBottomSheetDialog")
            }

        }
    }

    private fun checkFields(): Boolean {
        if (binding.carEdiText.text.isNullOrEmpty()) {
            viewModel.setErrorMessage(R.string.ERROR_SELECT_CAR)
            return false
        }

        if (binding.bagsPickButton.text.isNullOrEmpty()) {
            viewModel.setErrorMessage(R.string.ERROR_SELECT_SEAT)
            return false
        }

        return true
    }


    private fun onBagsItemClickListener(bagsStatusType: BagsStatusType) {
        viewModel.setBags(bagsStatusType)
    }



    private fun onCarItemClickListener(car: Car?) {
        if (car != null) {
            viewModel.setCar(car)
            carPickBottomSheetDialog.dismiss()
        } else {
            carPickBottomSheetDialog.dismiss()
            startActivityForResultWithFade(AddCarActivity::class, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadUserCars()
            }
        }
    }


}
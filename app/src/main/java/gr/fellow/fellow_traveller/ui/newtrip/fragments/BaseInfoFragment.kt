package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentBaseInfoBinding
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.CarPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.PetBottomSheetDialog
import gr.fellow.fellow_traveller.ui.findNavController
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener


class BaseInfoFragment : BaseFragment<FragmentBaseInfoBinding>() {

    private val viewModel: NewTripViewModel by activityViewModels()
    private var carList = mutableListOf<Car>()

    private lateinit var petBottomSheetDialog: PetBottomSheetDialog
    private lateinit var carPickBottomSheetDialog: CarPickBottomSheetDialog

    override fun getViewBinding(): FragmentBaseInfoBinding =
        FragmentBaseInfoBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        with(viewModel) {
            car.observe(viewLifecycleOwner, Observer {
                binding.carButton.setText(it.fullInfo)
            })

            seats.observe(viewLifecycleOwner, Observer {
                binding.seatsPickButton.currentNum = it
            })

            bags.observe(viewLifecycleOwner, Observer {
                binding.bagsPickButton.currentNum = it
            })

            pet.observe(viewLifecycleOwner, Observer {
                binding.pet.text = if (it) getString(R.string.allowed) else getString(R.string.not_allowed)
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

            ImageButtonNext.root.setOnClickListener {
                if (viewModel.car.value != null) {
                    findNavController()?.navigate(R.id.next_fragment)
                } else {
                    viewModel.setError(R.string.ERROR_SELECT_CAR)
                }

            }

            bagsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    viewModel.setBags(value)
                }
            }

            seatsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    viewModel.setSeats(value)
                }
            }

            pet.setOnClickListener {
                petBottomSheetDialog = PetBottomSheetDialog(this@BaseInfoFragment::onItemClickListener)
                petBottomSheetDialog.show(childFragmentManager, "petBottomSheetDialog")

            }


            carButton.setOnClickListener {
                carPickBottomSheetDialog = CarPickBottomSheetDialog(carList, this@BaseInfoFragment::onCarItemClickListener)
                carPickBottomSheetDialog.show(childFragmentManager, "carPickBottomSheetDialog")
            }

        }
    }


    private fun onItemClickListener(petAnswerType: PetAnswerType) {
        if (petAnswerType == PetAnswerType.Yes) {
            viewModel.setPet(true)

        } else {
            viewModel.setPet(false)
        }
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
package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentBaseInfoBinding
import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.domain.PetAnswerType
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.CarPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.PetBottomSheetDialog
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener


class BaseInfoFragment : Fragment() {

    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var carList = mutableListOf<Car>()
    private lateinit var mAdapter: CarTripsAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var dialog: Dialog
    private lateinit var petBottomSheetDialog: PetBottomSheetDialog
    private lateinit var carPickBottomSheetDialog: CarPickBottomSheetDialog

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentBaseInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBaseInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        newTripViewModel.loadUserCars()


        with(newTripViewModel) {
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

        with(binding) {

            ImageButtonNext.root.setOnClickListener {
                if (newTripViewModel.car.value != null) {
                    navController.navigate(R.id.next_fragment)
                } else {
                    newTripViewModel.setError(R.string.ERROR_SELECT_CAR)
                }

            }

            bagsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    newTripViewModel.setBags(value)
                }
            }

            seatsPickButton.pickButtonActionListener = object : PickButtonActionListener {
                override fun onPickAction(value: Int) {
                    newTripViewModel.setSeats(value)
                }
            }

            pet.setOnClickListener {
                petBottomSheetDialog = PetBottomSheetDialog(this@BaseInfoFragment::onItemClickListener)
                petBottomSheetDialog.show(childFragmentManager, "petBottomSheetDialog");

            }


            carButton.setOnClickListener {
                carPickBottomSheetDialog = CarPickBottomSheetDialog(carList, this@BaseInfoFragment::onCarItemClickListener)
                carPickBottomSheetDialog.show(childFragmentManager, "carPickBottomSheetDialog");
            }

        }
    }

    private fun onItemClickListener(petAnswerType: PetAnswerType) {
        if (petAnswerType == PetAnswerType.Yes) {
            newTripViewModel.setPet(true)

        } else {
            newTripViewModel.setPet(false)
        }
    }


    private fun onCarItemClickListener(car: Car?) {
        if (car != null) {
            newTripViewModel.setCar(car)
            carPickBottomSheetDialog.dismiss()
        } else {
            carPickBottomSheetDialog.dismiss()
            startActivityForResultWithFade(AddCarActivity::class, 1)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                newTripViewModel.loadUserCars()
            }
        }
    }

}
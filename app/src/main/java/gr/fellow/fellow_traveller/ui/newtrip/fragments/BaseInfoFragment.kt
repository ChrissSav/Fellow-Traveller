package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentBaseInfoBinding
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.ui.car.AddCarActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.views.PickButtonActionListener


class BaseInfoFragment : Fragment() {

    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var carList = mutableListOf<CarEntity>()
    private lateinit var mAdapter: CarTripsAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var dialog: Dialog

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
                binding.carButton.text = "${it.brand} ${it.model} | ${it.plate} | ${it.color}"
                binding.carButton.setTextColor(resources.getColor(R.color.button_fill_color))
            })

            seats.observe(viewLifecycleOwner, Observer {
                binding.seatsPickButton.currentNum = it


            })

            bags.observe(viewLifecycleOwner, Observer {
                binding.bagsPickButton.currentNum = it
            })


            pet.observe(viewLifecycleOwner, Observer {
                binding.switchPet.isChecked = it
            })

        }

        with(binding) {

            ImageButtonNext.setOnClickListener {
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

            switchPet.setOnCheckedChangeListener { _, b ->
                newTripViewModel.setPet(b)
            }


            carButton.setOnClickListener {
                openDialogForCar()
            }

        }
    }


    private fun openDialogForCar() {
        dialog = Dialog(requireActivity(), R.style.ThemeDialogNew)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.choose_car)
        dialog.setCancelable(true)
        val button = dialog.findViewById<Button>(R.id.add_car_button)
        dialog.show()
        button.setOnClickListener {
            val intent = Intent(activity, AddCarActivity::class.java)
            startActivityForResult(intent, 1)
        }

        buildRecyclerViewForCar(dialog)
    }


    private fun buildRecyclerViewForCar(dialog: Dialog) {
        mRecyclerView = dialog.findViewById(R.id.choose_car_recyclerView)
        mAdapter = CarTripsAdapter(carList) { car ->
            newTripViewModel.setCar(car)
            dialog.dismiss()
        }
        mRecyclerView.layoutManager = GridLayoutManager(context, 2);
        mRecyclerView.adapter = mAdapter

        newTripViewModel.carList.observe(viewLifecycleOwner, Observer {
            carList.clear()
            carList.addAll(it)
            mRecyclerView.adapter?.notifyDataSetChanged()
        })


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
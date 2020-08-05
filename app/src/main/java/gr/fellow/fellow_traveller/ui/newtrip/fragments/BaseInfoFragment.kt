package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentBaseInfoBinding
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


class BaseInfoFragment : Fragment() {
    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentBaseInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        with(newTripViewModel) {
            car.observe(viewLifecycleOwner, Observer {
            })

            seats.observe(viewLifecycleOwner, Observer {
                binding.seatsValueTv.text = it.toString()

            })

            bags.observe(viewLifecycleOwner, Observer {
                binding.bagsValueTv.text = it.toString()

            })


            pet.observe(viewLifecycleOwner, Observer {
                binding.switchPet.isChecked = it
            })

        }

        with(binding) {

            ImageButtonNext.setOnClickListener {
                navController.navigate(R.id.next_fragment)
            }

            plusButtonSeats.setOnClickListener {
                increaseSeats(seatsValueTv)
            }

            minusButtonSeats.setOnClickListener {
                decreaseSeats(seatsValueTv)
            }
            plusButtonBags.setOnClickListener {
                increaseBags(bagsValueTv)

            }

            minusButtonBags.setOnClickListener {
                decreaseBags(bagsValueTv)
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
        val dialog = Dialog(requireActivity(), R.style.ThemeDialogNew)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.choose_car)
        dialog.setCancelable(true)
        val button = dialog.findViewById<Button>(R.id.choose_car_button_add_car)
        //getCars(dialog)
        dialog.show()
        button.setOnClickListener {
            /*dialog.dismiss()
            val intent = Intent(activity, AddCarSettingsActivity::class.java)
            startActivityForResult(intent, 1)*/
        }
    }


    private fun buildRecyclerViewForCar(dialog: Dialog) {
        val mRecyclerView = dialog.findViewById<RecyclerView>(R.id.choose_car_recyclerView)
        mRecyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(activity)
        val mAdapter = CarAdapter(mExampleList)
        mRecyclerView.setLayoutManager(mLayoutManager)
        mRecyclerView.setAdapter(mAdapter)
        mAdapter.setOnItemClickListener(object : OnItemClickListener() {
            fun onItemClick(position: Int) {
                buttonCar.setTextColor(Color.parseColor(CLICK_COLOR))
                buttonCar.setText(mExampleList.get(position).getDescription())
                currentCar = mExampleList.get(position)
                dialog.dismiss()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun increaseSeats(textView: TextView) {
        val currentNum = textView.text.toString().toInt()
        textView.text = (currentNum + 1).toString()
        newTripViewModel.setSeats(currentNum + 1)
    }

    private fun decreaseSeats(textView: TextView) {
        val currentNum = textView.text.toString().toInt()
        if (currentNum > 1) {
            textView.text = (currentNum - 1).toString()
            newTripViewModel.setSeats(currentNum - 1)
        }
    }

    private fun increaseBags(textView: TextView) {
        val currentNum = textView.text.toString().toInt()
        textView.text = (currentNum + 1).toString()
        newTripViewModel.setBags(currentNum + 1)
    }

    private fun decreaseBags(textView: TextView) {
        val currentNum = textView.text.toString().toInt()
        if (currentNum > 0) {
            textView.text = (currentNum - 1).toString()
            newTripViewModel.setBags(currentNum - 1)
        }
    }

}
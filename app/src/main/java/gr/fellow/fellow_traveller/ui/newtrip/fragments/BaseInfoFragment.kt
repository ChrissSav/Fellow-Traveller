package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
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


        binding.ImageButtonNext.setOnClickListener {
            navController.navigate(R.id.next_fragment)
        }
        with(binding) {

            plusButtonSeats.setOnClickListener {
                increaseSeats(seatsValueTv )
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
        }
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
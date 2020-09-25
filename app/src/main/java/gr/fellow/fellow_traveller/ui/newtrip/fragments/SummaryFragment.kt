package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentSummaryBinding
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


class SummaryFragment : Fragment() {
    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val clickListener = View.OnClickListener { view ->

            when (view.tag) {
                "1" -> {
                    navController.navigate(R.id.action_summaryFragment_to_destinationsFragment)
                }
                "2" -> {
                    navController.navigate(R.id.action_summaryFragment_to_dateTimeFragment)
                }
                "3" -> {
                    navController.navigate(R.id.action_summaryFragment_to_baseInfoFragment)
                }
                "4" -> {
                    navController.navigate(R.id.action_summaryFragment_to_priceFragment)
                }
                "5" -> {
                    activity?.onBackPressed()
                }
                "6" -> {
                    navController.navigate(R.id.action_summaryFragment_to_pickUpFragment)
                }

            }
        }


        with(binding) {
            fromTextView.text = newTripViewModel.destinationFrom.value?.title
            toTextView.text = newTripViewModel.destinationTo.value?.title
            pickup.text = newTripViewModel.destinationPickUp.value?.title

            date.text = setDate(newTripViewModel.date.value.toString())
            time.text = setTime(newTripViewModel.time.value.toString())

            bags.text = newTripViewModel.bags.value.toString()
            seats.text = newTripViewModel.seats.value.toString()
            car.text = "${newTripViewModel.car.value?.brand} ${newTripViewModel.car.value?.model}"
            price.text = newTripViewModel.price.value.toString()
            msg.text = newTripViewModel.message.value.toString()
            pets.text = if (newTripViewModel.pet.value!!) resources.getString(R.string.allowed) else resources.getString(R.string.not_allowed)
            pets.setOnClickListener(clickListener)

             ImageButtonNext.root.setOnClickListener {
                 newTripViewModel.registerTrip()

             }



            TripSummaryFragmentConstraintLayoutFromTo.setOnClickListener(clickListener)
            constraintLayoutDate.setOnClickListener(clickListener)
            constraintLayoutTime.setOnClickListener(clickListener)
            constraintLayoutSeats.setOnClickListener(clickListener)
            bags.setOnClickListener(clickListener)
            pets.setOnClickListener(clickListener)
            price.setOnClickListener(clickListener)
            car.setOnClickListener(clickListener)
            pickup.setOnClickListener(clickListener)
            msg.setOnClickListener(clickListener)

        }

        newTripViewModel.success.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.next_fragment)
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDate(date: String): String {
        val month = date.substring(3, 5).toInt()
        var month_str = ""
        when (month) {
            1 -> month_str = "Ιαν"
            2 -> month_str = "Φεβ"
            3 -> month_str = "Μαρ"
            4 -> month_str = "Απρ"
            5 -> month_str = "Μαΐ"
            6 -> month_str = "Ιουν"
            7 -> month_str = "Ιουλ"
            8 -> month_str = "Αυγ"
            9 -> month_str = "Σεπ"
            10 -> month_str = "Οκτ"
            11 -> month_str = "Νοε"
            12 -> month_str = "Δεκ"
        }
        return "${date.substring(0, 2)}\n$month_str"
    }


    private fun setTime(time: String): String {
        var time = time
        val hourOfDay = time.substring(0, 2).toInt()
        val minute = time.substring(3, 5).toInt()
        time =
            """
            ${if (hourOfDay > 12) hourOfDay % 12 else hourOfDay}:${if (minute < 10) "0$minute" else minute}
            ${if (hourOfDay >= 12) "ΜΜ" else "ΠΜ"}
            """.trimIndent()
        return time
    }
}
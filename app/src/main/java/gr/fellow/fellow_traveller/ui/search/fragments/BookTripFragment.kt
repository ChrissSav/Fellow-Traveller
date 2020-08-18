package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentBookTripBinding
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel


class BookTripFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController
    private var _binding: FragmentBookTripBinding? = null
    private val binding get() = _binding!!
    private var index: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = requireArguments().getInt("indexTrip")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        searchTripViewModel.resultTrips.value?.get(index)?.let {
            with(it) {
                binding.startTextView.text = destFrom.title
                binding.endTextView.text = destTo.title
                binding.date.text = setDate(getDate())
                binding.time.text = setTime(getTime())
                binding.seatsTextView.text = (maxBags - currentBags).toString()
                binding.petsSwitch.isEnabled = hasPet
                binding.priceTextView.text = getString(R.string.price, price.toString())

                bagsHandle(maxBags - currentBags)
            }
        }

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }


    }

    private fun bagsHandle(bagsMax: Int) {

        with(binding) {

            bagsMinusButton.setOnClickListener {
                if (bagsValueTv.text.toString().toInt() > 0) {
                    bagsValueTv.text = (bagsValueTv.text.toString().toInt() - 1).toString()
                }
            }

            bagsPlusButton.setOnClickListener {
                if (bagsValueTv.text.toString().toInt() < bagsMax) {
                    bagsValueTv.text = (bagsValueTv.text.toString().toInt() + 1).toString()
                }
            }

        }
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
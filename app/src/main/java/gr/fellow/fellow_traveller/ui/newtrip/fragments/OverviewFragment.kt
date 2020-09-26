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
import gr.fellow.fellow_traveller.databinding.FragmentOverviewBinding
import gr.fellow.fellow_traveller.ui.getDayFromTimestamp
import gr.fellow.fellow_traveller.ui.getTimeFromTimestamp
import gr.fellow.fellow_traveller.ui.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


class OverviewFragment : Fragment(), View.OnClickListener {
    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        with(binding) {

            textViewFrom.text = newTripViewModel.destinationFrom.value?.title
            textViewTo.text = newTripViewModel.destinationTo.value?.title
            textViewPickUpPoint.text = newTripViewModel.destinationPickUp.value?.title
            day.text = getDayFromTimestamp(newTripViewModel.getTimestamp())
            time.text = getTimeFromTimestamp(newTripViewModel.getTimestamp())
            price.text = getString(R.string.price, newTripViewModel.price.value.toString())
            seats.text = newTripViewModel.seats.value.toString()
            bags.text = newTripViewModel.bags.value.toString()
            pet.text = if (newTripViewModel.pet.value!!) resources.getString(R.string.yes) else resources.getString(R.string.no)
            car.text = "${newTripViewModel.car.value?.brand} ${newTripViewModel.car.value?.model}"
            userImage.loadImageFromUrl(newTripViewModel.userInfo.value?.picture.toString())
            username.text = newTripViewModel.userInfo.value?.fullName
            message.text = if (newTripViewModel.message.value.toString().isNotEmpty())
                newTripViewModel.message.value.toString()
            else resources.getString(R.string.no_driver_message)




            ImageButtonNext.root.setOnClickListener {
                newTripViewModel.registerTrip()

            }



            textViewFrom.setOnClickListener(this@OverviewFragment)
            textViewTo.setOnClickListener(this@OverviewFragment)
            textViewPickUpPoint.setOnClickListener(this@OverviewFragment)
            day.setOnClickListener(this@OverviewFragment)
            time.setOnClickListener(this@OverviewFragment)
            price.setOnClickListener(this@OverviewFragment)
            bags.setOnClickListener(this@OverviewFragment)
            seats.setOnClickListener(this@OverviewFragment)
            pet.setOnClickListener(this@OverviewFragment)
            car.setOnClickListener(this@OverviewFragment)
            message.setOnClickListener(this@OverviewFragment)

        }

        newTripViewModel.success.observe(viewLifecycleOwner, Observer {
            navController.navigate(R.id.next_fragment)
        })


    }

    override fun onClick(view: View) {
        when (view.tag) {
            "1" -> {
                navController.navigate(R.id.action_fragment_overview_to_destinationsFragment)
            }
            "2" -> {
                navController.navigate(R.id.action_fragment_overview_to_dateTimeFragment)
            }
            "3" -> {
                navController.navigate(R.id.action_fragment_overview_to_baseInfoFragment)
            }
            "4" -> {
                navController.navigate(R.id.action_fragment_overview_to_priceFragment)
            }
            "5" -> {
                activity?.onBackPressed()
            }
            "6" -> {
                navController.navigate(R.id.action_fragment_overview_to_pickUpFragment)
            }

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
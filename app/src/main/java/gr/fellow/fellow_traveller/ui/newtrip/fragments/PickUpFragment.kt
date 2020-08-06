package gr.fellow.fellow_traveller.ui.newtrip.fragments

import android.app.Activity
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
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentPickUpBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PickUpFragment : Fragment() {

    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentPickUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.pickUpButton.setOnClickListener {
            val intent = Intent(activity, SelectLocationActivity::class.java)
            startActivityForResult(intent, 1)
        }

        if (newTripViewModel.destinationPickUp.value != null) {
            binding.pickUpButton.text = newTripViewModel.destinationPickUp.value?.title
            binding.pickUpButton.setTextColor(resources.getColor(R.color.button_fill_color))
        }

        newTripViewModel.destinationPickUp.observe(viewLifecycleOwner, Observer {
            binding.pickUpButton.text = it.title
            binding.pickUpButton.setTextColor(resources.getColor(R.color.button_fill_color))
        })

        binding.ImageButtonNext.setOnClickListener {

            if (newTripViewModel.destinationPickUp.value == null) {
                createSnackBar(view, "Παρακαλώ επιλέξτε σημείο αφετηρίας")
            }
            else {
                navController.navigate(R.id.next_fragment)
            }

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
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                newTripViewModel.setDestinationPickUp(id, title)
            }
        }
    }
}
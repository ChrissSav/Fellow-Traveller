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
import gr.fellow.fellow_traveller.databinding.FragmentDestinationsBinding
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel


class DestinationsFragment : Fragment() {

    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentDestinationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDestinationsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.fromButton.setOnClickListener {
            val intent = Intent(activity, SelectLocationActivity::class.java)
            startActivityForResult(intent, 1)
        }

        binding.toButton.setOnClickListener {
            val intent = Intent(activity, SelectLocationActivity::class.java)
            startActivityForResult(intent, 2)
        }



        newTripViewModel.destinationFrom.observe(viewLifecycleOwner, Observer {
            binding.fromButton.text = it.title
            binding.fromButton.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        newTripViewModel.destinationTo.observe(viewLifecycleOwner, Observer {
            binding.toButton.text = it.title
            binding.toButton.setTextColor(resources.getColor(R.color.button_fill_color))

        })

        binding.ImageButtonNext.setOnClickListener {

            when {
                newTripViewModel.destinationFrom.value == null -> {
                    createSnackBar(view, "Παρακαλώ επιλέξτε σημείο αφετηρίας")
                }
                newTripViewModel.destinationTo.value == null -> {
                    createSnackBar(view, "Παρακαλώ επιλέξτε σημείο προορισμού")
                }
                else -> {
                    navController.navigate(R.id.next_fragment)
                }
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
                newTripViewModel.setDestinationFrom(id, title)
            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title").toString()
                val id = data?.getStringExtra("id").toString()
                newTripViewModel.setDestinationTo(id, title)

            }

        }
    }

}
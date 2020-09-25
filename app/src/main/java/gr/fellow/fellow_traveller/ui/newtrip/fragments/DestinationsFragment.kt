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
import gr.fellow.fellow_traveller.ui.location.SelectLocationActivity
import gr.fellow.fellow_traveller.ui.newtrip.NewTripViewModel
import gr.fellow.fellow_traveller.ui.startActivityForResultWithFade


class DestinationsFragment : Fragment() {

    private val newTripViewModel: NewTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    /**
     * This property is only valid between onCreateView and
     * onDestroyView.
     */
    private var _binding: FragmentDestinationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDestinationsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.editTextFrom.onClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 1)
        }

        binding.editTextTo.onClickListener {
            startActivityForResultWithFade(SelectLocationActivity::class, 2)

        }


        newTripViewModel.destinationFrom.observe(viewLifecycleOwner, Observer {
            binding.editTextFrom.text = it.title
        })

        newTripViewModel.destinationTo.observe(viewLifecycleOwner, Observer {
            binding.editTextTo.text = it.title
        })

        binding.ImageButtonNext.root.setOnClickListener {

            when {
                newTripViewModel.destinationFrom.value == null -> {
                    newTripViewModel.setError(R.string.ERROR_SELECT_DEST_FROM)
                }
                newTripViewModel.destinationTo.value == null -> {
                    newTripViewModel.setError(R.string.ERROR_SELECT_DEST_TO)
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
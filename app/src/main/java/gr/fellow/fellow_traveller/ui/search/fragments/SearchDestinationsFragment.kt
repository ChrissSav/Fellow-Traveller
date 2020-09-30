package gr.fellow.fellow_traveller.ui.search.fragments

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
import gr.fellow.fellow_traveller.databinding.FragmentSearchDestinationsBinding
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.createAlerter
import gr.fellow.fellow_traveller.ui.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.locations.SelectDestinationActivity


class SearchDestinationsFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    private var _binding: FragmentSearchDestinationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSearchDestinationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.destFromButton.setOnClickListener {
            val intent = Intent(activity, SelectDestinationActivity::class.java)
            startActivityForResult(intent, 1)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }
        binding.destToButton.setOnClickListener {
            val intent = Intent(activity, SelectDestinationActivity::class.java)
            intent.putExtra("info", "to")
            startActivityForResult(intent, 2)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.searchButton.setOnClickListener {
            if (searchTripViewModel.destinationFrom.value != null && searchTripViewModel.destinationTo.value != null) {
                searchTripViewModel.updateFilter()
                navController.navigate(R.id.next_fragment)
            } else {
                createAlerter(resources.getString(R.string.ERROR_FIELDS_REQUIRE))
            }
        }




        with(searchTripViewModel) {
            destinationFrom.observe(viewLifecycleOwner, Observer {
                binding.destFromButton.setText(it.title)
            })

            destinationTo.observe(viewLifecycleOwner, Observer {
                binding.destToButton.setText(it.title)
            })


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
                val place = data?.getParcelableExtra<PlaceModel>("place")
                place?.let {
                    searchTripViewModel.setDestinationFrom(it)
                }
            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val place = data?.getParcelableExtra<PlaceModel>("place")
                place?.let {
                    searchTripViewModel.setDestinationTo(it)
                }

            }

        }
    }


}
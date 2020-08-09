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
import gr.fellow.fellow_traveller.ui.createSnackBar
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.locations.SelectDestinationActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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
        }
        binding.destToButton.setOnClickListener {
            val intent = Intent(activity, SelectDestinationActivity::class.java)
            intent.putExtra("info", "to")
            startActivityForResult(intent, 2)
        }

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.searchButton.setOnClickListener {
            if (searchTripViewModel.destinationFrom.value != null && searchTripViewModel.destinationTo.value != null)
                navController.navigate(R.id.next_fragment)
            else
                createSnackBar(view, resources.getString(R.string.ERROR_FIELDS_REQUIRE))
        }




        with(searchTripViewModel) {
            destinationFrom.observe(viewLifecycleOwner, Observer {
                binding.destFromButton.text = it.title
                binding.destFromButton.setTextColor(resources.getColor(R.color.button_fill_color))
            })

            destinationTo.observe(viewLifecycleOwner, Observer {
                binding.destToButton.text = it.title
                binding.destToButton.setTextColor(resources.getColor(R.color.button_fill_color))
            })


            load.observe(viewLifecycleOwner, Observer {
                binding.progressLoad.visibility = View.VISIBLE
            })

            error.observe(viewLifecycleOwner, Observer {
                binding.progressLoad.visibility = View.GONE
                createSnackBar(view, it)
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
                // newTripViewModel.setDestinationFrom(id, title)
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
package gr.fellow.fellow_traveller.ui.search.fragments

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
import gr.fellow.fellow_traveller.databinding.FragmentSearchResultsBinding
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchResultsFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    private var _binding: FragmentSearchResultsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.filterButton.setOnClickListener {
            navController.navigate(R.id.action_searchResultsFragment_to_searchFilterFragment)
        }

        binding.closeButton.setOnClickListener {
            activity?.onBackPressed()
        }


        with(searchTripViewModel) {
            destinationTo.observe(viewLifecycleOwner, Observer {
                binding.toButton.text = it.title
            })


            destinationFrom.observe(viewLifecycleOwner, Observer {
                binding.fromButton.text = it.title
            })
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
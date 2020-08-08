package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentSearchResultsBinding


class SearchResultsFragment : Fragment() {


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

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
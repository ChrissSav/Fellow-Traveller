package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.FragmentSearchDestinationsBinding


class SearchDestinationsFragment : Fragment() {


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


        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.searchButton.setOnClickListener {
            navController.navigate(R.id.next_fragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
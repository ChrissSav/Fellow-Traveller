package gr.fellow.fellow_traveller.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.databinding.FragmentSearchResultsBinding
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.SearchResultsAdapter

@AndroidEntryPoint
class SearchResultsFragment : Fragment() {

    private val searchTripViewModel: SearchTripViewModel by activityViewModels()
    private lateinit var navController: NavController

    private var _binding: FragmentSearchResultsBinding? = null
    private val binding get() = _binding!!
    private var tripsList = mutableListOf<Trip>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        with(binding) {
            ImageButtonScrollToTop.setOnClickListener {
                appBarLayout.setExpanded(true, true)
            }
            filterButton.setOnClickListener {
                navController.navigate(R.id.action_searchResultsFragment_to_searchFilterFragment)
            }

            closeButton.setOnClickListener {
                activity?.onBackPressed()
            }


            swapButton.setOnClickListener {

                searchTripViewModel.swapDestinations()
            }

            recyclerView.adapter = SearchResultsAdapter(tripsList) {
                navController.navigate(
                    R.id.action_searchResultsFragment_to_searchTripDetailsFragment, bundleOf("tripId" to it.id)
                )
            }
        }





        with(searchTripViewModel) {


            resultTrips.observe(viewLifecycleOwner, Observer {
                tripsList.clear()
                binding.recyclerView.adapter?.notifyDataSetChanged()
                if (it.isNotEmpty()) {
                    tripsList.addAll(it)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.notFoundImage.visibility = View.GONE
                    binding.resultsLabel.text = getString(R.string.trip_found, it.size.toString())
                    binding.resultsLabel.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.notFoundImage.visibility = View.VISIBLE
                    binding.resultsLabel.visibility = View.INVISIBLE
                }
            })


            loadTest.observe(viewLifecycleOwner, Observer {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.notFoundImage.visibility = View.GONE
                } else
                    binding.progressBar.visibility = View.GONE
            })





            searchFilter.observe(viewLifecycleOwner, Observer {
                if (filterFlag)
                    searchTripViewModel.getTrips()
            })


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
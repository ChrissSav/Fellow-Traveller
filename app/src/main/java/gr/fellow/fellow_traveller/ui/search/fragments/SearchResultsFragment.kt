package gr.fellow.fellow_traveller.ui.search.fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.databinding.FragmentSearchResultsBinding
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.SearchResultsAdapter

@AndroidEntryPoint
class SearchResultsFragment : BaseFragment<FragmentSearchResultsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripsList = mutableListOf<Trip>()


    override fun getViewBinding(): FragmentSearchResultsBinding =
        FragmentSearchResultsBinding.inflate(layoutInflater)

    override fun setUpObservers() {


        with(viewModel) {


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


            load.observe(viewLifecycleOwner, Observer {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.notFoundImage.visibility = View.GONE
                } else
                    binding.progressBar.visibility = View.GONE
            })





            searchFilter.observe(viewLifecycleOwner, Observer {
                if (filterFlag)
                    viewModel.getTrips()
            })


            destinationTo.observe(viewLifecycleOwner, Observer {
                binding.toButton.text = it.title
            })


            destinationFrom.observe(viewLifecycleOwner, Observer {
                binding.fromButton.text = it.title
            })


        }
    }

    override fun setUpViews() {
        with(binding) {
            ImageButtonScrollToTop.setOnClickListener {
                appBarLayout.setExpanded(true, true)
            }
            filterButton.setOnClickListener {
                findNavController()?.navigate(R.id.action_searchResultsFragment_to_searchFilterFragment)
            }

            closeButton.setOnClickListener {
                onBackPressed()
            }


            swapButton.setOnClickListener {

                viewModel.swapDestinations()
            }

            recyclerView.adapter = SearchResultsAdapter(tripsList) {
                findNavController()?.navigate(
                    R.id.action_searchResultsFragment_to_searchTripDetailsFragment, bundleOf("tripId" to it.id)
                )
            }
        }
    }

}
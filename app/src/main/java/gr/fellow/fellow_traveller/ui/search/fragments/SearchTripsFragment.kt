package gr.fellow.fellow_traveller.ui.search.fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripsBinding
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.search.SearchFilterActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.SearchResultsAdapter
import gr.fellow.fellow_traveller.ui.search.locations.SelectDestinationActivity


@AndroidEntryPoint
class SearchTripsFragment : BaseFragment<FragmentSearchTripsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripsList = mutableListOf<TripSearch>()

    override fun getViewBinding(): FragmentSearchTripsBinding =
        FragmentSearchTripsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        with(viewModel) {

            binding.recyclerView.adapter = SearchResultsAdapter(tripsList, this@SearchTripsFragment::onTripClicked)


            destinations.observe(viewLifecycleOwner, Observer {
                binding.destFromButton.text = it.first?.title
                binding.destToButton.text = it.second?.title
            })

            errorSecond.observe(viewLifecycleOwner, Observer {
                if (it.internal)
                    createAlerter(getString(it.messageId))
                else
                    createAlerter(it.message)
            })

            load.observe(viewLifecycleOwner, Observer {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.notFoundImage.visibility = View.GONE
                } else
                    binding.progressBar.visibility = View.GONE
            })

            resultTrips.observe(viewLifecycleOwner, Observer {

                binding.resultsLabel.visibility = View.VISIBLE
                binding.sortButton.visibility = View.VISIBLE

                tripsList.clear()
                tripsList.addAll(it)
                binding.recyclerView.adapter?.notifyDataSetChanged()
                binding.resultsLabel.text = getString(R.string.trip_found, it.size.toString())
                if (it.isNotEmpty()) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.notFoundImage.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.notFoundImage.visibility = View.VISIBLE
                }
            })

            searchFilter.observe(viewLifecycleOwner, Observer {
                binding.filterButton.visibility = View.VISIBLE
                viewModel.getTrips()
            })

        }
    }


    private fun onTripClicked(item: TripSearch) {

    }


    override fun setUpViews() {
        with(binding) {

            label.setOnClickListener {
                binding.motion.transitionToStart()
            }

            destFromButton.onClickListener {
                startActivityForResultWithFade(SelectDestinationActivity::class, 1)
            }
            destToButton.onClickListener {
                val intent = Intent(activity, SelectDestinationActivity::class.java)
                intent.putExtra("info", "to")
                startActivityForResultWithFade(intent, 2)
            }

            backButton.setOnClickListener {
                onBackPressed()
            }

            searchButton.setOnClickListener {
                if (viewModel.destinations.value?.first != null && viewModel.destinations.value?.second != null) {
                    viewModel.updateFilter()
                    motion.transitionToEnd()
                } else {
                    createAlerter(resources.getString(R.string.ERROR_FIELDS_REQUIRE))
                }
            }

            filterButton.setOnClickListener {
                viewModel.searchFilter.value?.let {
                    val intent = Intent(activity, SearchFilterActivity::class.java)
                    intent.putExtra("filter", it)
                    startActivityToLeft(intent, 3)
                }

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = data?.getParcelableExtra<PlaceModel>("place")
                place?.let {
                    viewModel.setDestinationFrom(it)
                }
            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val place = data?.getParcelableExtra<PlaceModel>("place")
                place?.let {
                    viewModel.setDestinationTo(it)
                }

            }

        } else if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                val filter = data?.getParcelableExtra<SearchTripFilter>("filter")

                filter?.let {
                    createToast(it.toString())
                    viewModel.updateFilter(it)
                }

            }

        }
    }


}
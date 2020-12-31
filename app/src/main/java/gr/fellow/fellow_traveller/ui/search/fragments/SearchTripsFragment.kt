package gr.fellow.fellow_traveller.ui.search.fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchTripsBinding
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.domain.SortAnswerType
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.SortSearchTripsBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.*
import gr.fellow.fellow_traveller.ui.search.SearchFilterActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.adapter.SearchResultsListAdapter
import gr.fellow.fellow_traveller.ui.search.locations.SelectDestinationActivity
import gr.fellow.fellow_traveller.utils.currentTimeStamp


@AndroidEntryPoint
class SearchTripsFragment : BaseFragment<FragmentSearchTripsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var clickTime = 0L
    private var clickTimeDialog = 0L
    private var bundle = bundleOf()
    private lateinit var sortSearchTripsBottomSheetDialog: SortSearchTripsBottomSheetDialog

    override fun getViewBinding(): FragmentSearchTripsBinding =
        FragmentSearchTripsBinding.inflate(layoutInflater)


    override fun setUpObservers() {


        with(viewModel) {
            destinations.observe(viewLifecycleOwner, Observer {
                binding.destFromButton.text = it.first?.title
                binding.destToButton.text = it.second?.title
            })

            error.observe(viewLifecycleOwner, Observer {
                binding.progressBar.visibility = View.GONE
                if (it.internal)
                    createAlerter(getString(it.messageId))
                else
                    createAlerter(it.message)
            })


            destinationsIsSet.observe(viewLifecycleOwner, Observer {
                if (it) {
                    viewModel.updateFilter()
                } else {
                    viewModel.destinationsIsSet.value = false
                }
            })


            loadResults.observe(viewLifecycleOwner, Observer {
                if (it) {
                    binding.sortButton.visibility = View.GONE
                    binding.notFoundImage.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            })


            resultTrips.observe(viewLifecycleOwner, Observer {

                (binding.recyclerView.adapter as SearchResultsListAdapter).submitList(it)


                binding.resultsLabel.text = getString(R.string.trip_found, it.size.toString())
                binding.resultsLabel.visibility = View.VISIBLE

                if (it.isNotEmpty()) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.sortButton.visibility = View.VISIBLE
                    binding.notFoundImage.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.notFoundImage.visibility = View.VISIBLE
                }


            })

            searchFilter.observe(viewLifecycleOwner, {
                binding.filterButton.visibility = View.VISIBLE
                binding.motion.getTransition(R.id.search_trip_transition).setEnable(true)
                viewModel.getTrips()
            })

        }
    }


    override fun setUpViews() {

        with(binding) {

            motion.getTransition(R.id.search_trip_transition).setEnable(false)
            recyclerView.adapter = SearchResultsListAdapter(this@SearchTripsFragment::onTripClicked)

            sortButton.setOnClickListener {
                //Check because dialog open twice
                if (currentTimeStamp() - clickTimeDialog > 1) {
                    clickTimeDialog = currentTimeStamp()
                    sortSearchTripsBottomSheetDialog = SortSearchTripsBottomSheetDialog(this@SearchTripsFragment::onSortItemClickListener)
                    sortSearchTripsBottomSheetDialog.show(childFragmentManager, "sortSearchTripsBottomSheetDialog")
                }
            }

            label.setOnClickListener {
                binding.motion.transitionToStart()
            }

            destFromButton.addOnClickListener {
                startActivityForResultWithFade(SelectDestinationActivity::class, 1)
            }

            destToButton.addOnClickListener {
                startActivityForResultWithFade(SelectDestinationActivity::class, 2)
            }


            backButton.setOnClickListener {
                onBackPressed()
            }

            searchButton.setOnClickListener {
                if (destFromButton.isCorrect() and destToButton.isCorrect()) {
                    viewModel.updateFilter()
                    viewModel.getTrips()
                    motion.transitionToEnd()
                }
            }

            filterButton.setOnClickListener {
                viewModel.searchFilter.value?.let {
                    val intent = Intent(activity, SearchFilterActivity::class.java)
                    intent.putExtra("filter", it)
                    startActivityToLeft(intent, 3)
                }

            }

            swap.setOnClickListener {
                if (!destFromButton.text.isNullOrEmpty() && !destToButton.text.isNullOrEmpty()) {
                    viewModel.swapDestinations()
                    motion.transitionToEnd()
                    viewModel.getTrips()
                }
            }
        }

    }


    /** Because is detect twice click **/
    private fun onTripClicked(item: TripSearch) {
        if (currentTimeStamp() - clickTime > 1) {
            clickTime = currentTimeStamp()
            findNavController()?.navigate(R.id.action_searchTripsFragment_to_searchTripDetailsFragment, bundleOf("trip" to item))
        }
    }


    override fun onStop() {
        bundle.putFloat("motionCurrentState", binding.motion.progress)
        bundle.putInt("resultsLabelVisibility", binding.resultsLabel.visibility)
        bundle.putInt("sortButtonVisibility", binding.sortButton.visibility)
        super.onStop()

    }

    override fun onResume() {
        binding.motion.progress = bundle.getFloat("motionCurrentState", 0f)
        binding.resultsLabel.visibility = bundle.getInt("resultsLabelVisibility", View.GONE)
        binding.sortButton.visibility = bundle.getInt("sortButtonVisibility", View.GONE)
        super.onResume()
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
                    viewModel.updateFilter(it)
                }

            }

        }
    }

    private fun onSortItemClickListener(sortAnswerType: SortAnswerType) {
        binding.sortButton.text = getString(sortAnswerType.textInt)
        viewModel.setSortOption(sortAnswerType, null)
    }

}
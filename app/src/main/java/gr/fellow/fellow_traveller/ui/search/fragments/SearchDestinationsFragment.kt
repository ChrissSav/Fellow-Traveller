package gr.fellow.fellow_traveller.ui.search.fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentSearchDestinationsBinding
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.dialogs.bottom_sheet.SearchTripFilterPickBottomSheetDialog
import gr.fellow.fellow_traveller.ui.extensions.createAlerter
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.extensions.startActivityForResultWithFade
import gr.fellow.fellow_traveller.ui.extensions.startActivityToLeft
import gr.fellow.fellow_traveller.ui.search.SearchFilterActivity
import gr.fellow.fellow_traveller.ui.search.SearchTripViewModel
import gr.fellow.fellow_traveller.ui.search.locations.SelectDestinationActivity


@AndroidEntryPoint
class SearchDestinationsFragment : BaseFragment<FragmentSearchDestinationsBinding>() {

    private val viewModel: SearchTripViewModel by activityViewModels()
    private var tripsList = mutableListOf<TripSearch>()
    private lateinit var searchTripFilterPickBottomSheetDialog: SearchTripFilterPickBottomSheetDialog

    override fun getViewBinding(): FragmentSearchDestinationsBinding =
        FragmentSearchDestinationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {
        with(viewModel) {
            destinationFrom.observe(viewLifecycleOwner, Observer {
                binding.destFromButton.text = it.title
            })

            destinationTo.observe(viewLifecycleOwner, Observer {
                binding.destToButton.text = it.title
            })

            load.observe(viewLifecycleOwner, Observer {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })

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
        }
    }

    override fun setUpViews() {
        with(binding) {
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
                if (viewModel.destinationFrom.value != null && viewModel.destinationTo.value != null) {
                    viewModel.updateFilter()
                    viewModel.getTrips()
                    motion.transitionToEnd()
                } else {
                    createAlerter(resources.getString(R.string.ERROR_FIELDS_REQUIRE))
                }
            }

            filterButton.setOnClickListener {
                startActivityToLeft(SearchFilterActivity::class)
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

        }
    }


}
package gr.fellow.fellow_traveller.ui.home.trip

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentTripInvolvedHistoryBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.onBackPressed
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.TripsAsPassengerAdapter


@AndroidEntryPoint
class TripInvolvedHistoryFragment : BaseFragment<FragmentTripInvolvedHistoryBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val args: TripInvolvedHistoryFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentTripInvolvedHistoryBinding =
        FragmentTripInvolvedHistoryBinding.inflate(layoutInflater)

    override fun setUpObservers() {
        if (args.creator) {
            viewModel.tripsAsCreatorHistory.observe(viewLifecycleOwner, Observer { list ->
                binding.swipeRefreshLayout.isRefreshing = false
                (binding.recyclerView.adapter as TripsAsPassengerAdapter).submitList(list)
            })
        } else {
            viewModel.tripsAsPassengerHistory.observe(viewLifecycleOwner, Observer { list ->
                binding.swipeRefreshLayout.isRefreshing = false
                (binding.recyclerView.adapter as TripsAsPassengerAdapter).submitList(list)
            })
        }

        viewModel.loadHistory.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar2.visibility = View.VISIBLE
                binding.recyclerView.scrollToPosition((binding.recyclerView.adapter as TripsAsPassengerAdapter).currentList.size - 1)
            } else
                binding.progressBar2.visibility = View.GONE
        })


    }

    override fun setUpViews() {
        if (args.creator) {
            viewModel.loadTripsAsCreatorHistory()
            binding.recyclerView.adapter = TripsAsPassengerAdapter(R.color.LightAqua, this@TripInvolvedHistoryFragment::onTripClick)
            binding.label.text = "Ιστορικό προσφορών"
        } else {
            viewModel.loadTripsAsPassengerHistory()
            binding.recyclerView.adapter = TripsAsPassengerAdapter(onTripClickListener = this@TripInvolvedHistoryFragment::onTripClick)
            binding.label.text = "Ιστορικό αναζητήσεων"
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            if (args.creator)
                viewModel.loadTripsAsCreatorHistoryClear()
            else
                viewModel.loadTripsAsPassengerHistoryClear()

            (binding.recyclerView.adapter as TripsAsPassengerAdapter).submitList(null)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (args.creator) {
                        viewModel.loadTripsAsCreatorHistory(true)
                    } else {
                        viewModel.loadTripsAsPassengerHistory(true)
                    }

                }
            }

        })
    }

    private fun onTripClick(tripInvolved: TripInvolved) {
        findNavController()?.navigate(R.id.action_tripInvolvedHistoryFragment_to_tripInvolvedDetailsFragment, bundleOf("trip" to tripInvolved, "creator" to args.creator))
    }

}
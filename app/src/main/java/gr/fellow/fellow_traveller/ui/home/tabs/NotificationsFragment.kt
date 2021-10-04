package gr.fellow.fellow_traveller.ui.home.tabs

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentNotificationsBinding
import gr.fellow.fellow_traveller.domain.NotificationStatus
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.extensions.findNavController
import gr.fellow.fellow_traveller.ui.extensions.startActivityWithFade
import gr.fellow.fellow_traveller.ui.extensions.startShimmerWithVisibility
import gr.fellow.fellow_traveller.ui.extensions.stopShimmerWithVisibility
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.NotificationAdapter
import gr.fellow.fellow_traveller.ui.rate.RateActivity


@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var loadMoreTripsAsCreator = true
    private var loadMoreTripsAsPassenger = true
    private val notifications = mutableListOf<Notification>()

    override fun getViewBinding(): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.notifications.observe(viewLifecycleOwner, {
            notifications.clear()
            notifications.addAll(it)
            (binding.recyclerView.adapter as NotificationAdapter).notifyDataSetChanged()
            if (it.isNullOrEmpty())
                binding.notificationsSection.visibility = View.VISIBLE
            else
                binding.notificationsSection.visibility = View.GONE
        })

        viewModel.loadNotifications.observe(viewLifecycleOwner, {
            if (it) {
                binding.notificationsSection.visibility = View.GONE
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
                binding.swipeRefreshLayout.isRefreshing = false
            }

        })

        viewModel.notification.observe(viewLifecycleOwner, {
            when (it.type) {
                NotificationStatus.RATE -> {
                    activity?.startActivityWithFade(RateActivity::class, bundleOf("notification" to it))
                }
                NotificationStatus.PASSENGER_EXIT, NotificationStatus.PASSENGER_ENTER -> {
                    findNavController()?.navigate(
                        R.id.action_destination_notifications_to_tripInvolvedDetailsFragment,
                        bundleOf("reload" to (!it.isRead), "history" to (it.trip.status == TripStatus.COMPLETED), "tripId" to it.trip.id, "creator" to true)
                    )
                }
                else -> {
                    // Nothing
                }
            }
        })

    }

    override fun setUpViews() {
        viewModel.loadNotifications()
        binding.recyclerView.adapter = NotificationAdapter(notifications, viewModel::readNotification)
        binding.recyclerView.hasFixedSize()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadMoreTripsAsCreator = true
            loadMoreTripsAsPassenger = true
            notifications.clear()
            (binding.recyclerView.adapter as NotificationAdapter).notifyDataSetChanged()
            viewModel.loadNotifications(true)
        }
        binding.back.button.setOnClickListener {
            activity?.onBackPressed()
        }
    }


    override fun onDestroyView() {
        (activity as HomeActivity).readAllUnReadNotification()
        super.onDestroyView()
    }

}
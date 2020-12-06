package gr.fellow.fellow_traveller.ui.home.tabs

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.NotificationAdapter
import gr.fellow.fellow_traveller.ui.rate.RateActivity


@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var loadMoreTripsAsCreator = true
    private var loadMoreTripsAsPassenger = true

    override fun getViewBinding(): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.notifications.observe(viewLifecycleOwner, Observer { list ->
            binding.swipeRefreshLayout.isRefreshing = false
            (binding.recyclerView.adapter as NotificationAdapter).submitList(list)

            //if there are no notifications to display, show specific message, else show notifications
            if (list.isNullOrEmpty())
                binding.notificationsSection.visibility = View.VISIBLE
            else
                binding.notificationsSection.visibility = View.GONE

        })

        viewModel.loadNotifications.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.notificationsSection.visibility = View.GONE
                binding.shimmerViewContainer.startShimmerWithVisibility()
            } else {
                binding.shimmerViewContainer.stopShimmerWithVisibility()
                binding.swipeRefreshLayout.isRefreshing = false
            }

        })

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as NotificationAdapter).readNotification(it)
            when (it.type) {
                NotificationStatus.RATE.code -> {
                    activity?.startActivityWithFade(RateActivity::class, bundleOf("notification" to it))
                }
                NotificationStatus.PASSENGER_EXIT.code, NotificationStatus.PASSENGER_ENTER.code -> {
                    findNavController()?.navigate(
                        R.id.action_destination_notifications_to_tripInvolvedDetailsFragment,
                        bundleOf("reload" to (!it.isRead), "history" to (it.trip.status == TripStatus.COMPLETED.code), "tripId" to it.trip.id, "creator" to true)
                    )
                }
            }
        })

    }

    override fun setUpViews() {
        viewModel.loadNotifications()
        binding.recyclerView.adapter = NotificationAdapter(viewModel::readNotification)


        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as NotificationAdapter).submitList(mutableListOf<Notification>())
            loadMoreTripsAsCreator = true
            loadMoreTripsAsPassenger = true
            viewModel.loadNotifications(true)
            (binding.recyclerView.adapter as NotificationAdapter).submitList(null)

        }
    }


}
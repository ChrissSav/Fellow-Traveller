package gr.fellow.fellow_traveller.ui.home.tabs

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentNotificationsBinding
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.extensions.startActivityWithFade
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.NotificationAdapter
import gr.fellow.fellow_traveller.ui.rate.RateActivity


@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {


        viewModel.notifications.observe(viewLifecycleOwner, Observer { list ->
            binding.swipeRefreshLayout.isRefreshing = false
            (binding.recyclerView.adapter as NotificationAdapter).submitList(list)

            if (list.filter { (it.type == 1 || it.type == 2) && !it.isRead }.any())
                viewModel.loadTripsAsCreatorClear()
            if (list.filter { it.type == 3 && !it.isRead }.any())
                viewModel.loadTripsAsPassengerClear()
        })

        viewModel.loadNotifications.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar2.visibility = View.VISIBLE
                binding.recyclerView.scrollToPosition((binding.recyclerView.adapter as NotificationAdapter).currentList.size - 1)
            } else {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.progressBar2.visibility = View.GONE
            }

        })

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as NotificationAdapter).readNotification(it)
            when (it.type) {
                0 -> {
                    activity?.startActivityWithFade(RateActivity::class, bundleOf("notification" to it))
                }
                /*   1 -> {
                       binding.description.setTextHtml(binding.description.context.getString(R.string.notification_passenger_exit, item.user.fullName))
                   }
                   2 -> {
                        binding.description.setTextHtml(binding.description.context.getString(R.string.notification_passenger_enter, item.user.fullName))
                    }
                    else -> {
                        binding.description.setTextHtml(binding.description.context.getString(R.string.notification_delete_trip, item.user.fullName))
                    }*/
            }
        })

    }

    override fun setUpViews() {
        viewModel.loadNotifications()
        binding.recyclerView.adapter = NotificationAdapter(viewModel::readNotification)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if ((binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() != 0)
                        viewModel.loadNotifications(true)
                }
            }

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as NotificationAdapter).submitList(mutableListOf<Notification>())

            viewModel.loadNotificationsClear()
        }
    }


}
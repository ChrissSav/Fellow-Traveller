package gr.fellow.fellow_traveller.ui.home.tabs

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.data.base.BaseFragment
import gr.fellow.fellow_traveller.databinding.FragmentNotificationsBinding
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.home.HomeViewModel
import gr.fellow.fellow_traveller.ui.home.adapter.NotificationAdapter

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun getViewBinding(): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(layoutInflater)


    override fun setUpObservers() {

        viewModel.loadNotifications.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar2.visibility = View.VISIBLE
                binding.recyclerView.scrollToPosition((binding.recyclerView.adapter as NotificationAdapter).currentList.size - 1)
            } else
                binding.progressBar2.visibility = View.GONE
        })

        viewModel.notifications.observe(viewLifecycleOwner, Observer { list ->
            binding.swipeRefreshLayout.isRefreshing = false
            (binding.recyclerView.adapter as NotificationAdapter).submitList(list)
        })
    }

    override fun setUpViews() {
        viewModel.loadNotifications()
        binding.recyclerView.adapter = NotificationAdapter(this@NotificationsFragment::onNotificationClick)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.loadNotifications(true)
                }
            }

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as NotificationAdapter).submitList(null)
            viewModel.loadNotificationsClear()

        }
    }


    private fun onNotificationClick(notification: Notification) {
    }

}
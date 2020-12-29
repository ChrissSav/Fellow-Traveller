package gr.fellow.fellow_traveller.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.NotificationItemLayoutBinding
import gr.fellow.fellow_traveller.domain.NotificationStatus
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.extensions.NotificationDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.setTextHtml
import gr.fellow.fellow_traveller.utils.getTimeTiDisplay


class NotificationAdapter(
    private val onItemClickListener: (Notification) -> Unit
) : ListAdapter<Notification, NotificationAdapter.ViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotificationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val item = currentList[position]

            binding.picture.loadImageFromUrl(item.user.picture)
            binding.trip.text = "${item.trip.destinationFrom} - ${item.trip.destinationTo}"


            when (item.type) {
                NotificationStatus.RATE.code -> {
                    binding.description.setTextHtml(binding.description.context.getString(R.string.notification_to_rate, item.user.fullName))
                }
                NotificationStatus.PASSENGER_EXIT.code -> {
                    binding.description.setTextHtml(binding.description.context.getString(R.string.notification_passenger_exit, item.user.fullName))
                }
                NotificationStatus.PASSENGER_ENTER.code -> {
                    binding.description.setTextHtml(binding.description.context.getString(R.string.notification_passenger_enter, item.user.fullName))
                }
                else -> {
                    binding.description.setTextHtml(binding.description.context.getString(R.string.notification_delete_trip, item.user.fullName))
                }
            }

            if (item.isRead)
                binding.read.visibility = View.INVISIBLE
            else
                binding.read.visibility = View.VISIBLE


            binding.time.text = getTimeTiDisplay(item.timestamp, binding.time.context)

            binding.trip.text = "${item.trip.destinationFrom} - ${item.trip.destinationTo}"

            binding.root.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }
    }


    class ViewHolder(val binding: NotificationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


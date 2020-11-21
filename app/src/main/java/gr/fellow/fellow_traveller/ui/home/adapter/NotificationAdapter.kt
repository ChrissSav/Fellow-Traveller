package gr.fellow.fellow_traveller.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.NotificationItemLayoutBinding
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.extensions.NotificationDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.setTextHtml
import gr.fellow.fellow_traveller.utils.currentTimeStamp


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
                0 -> {
                    binding.description.setTextHtml(binding.description.context.getString(R.string.notification_to_rate, item.user.fullName))
                }
                1 -> {
                    binding.description.setTextHtml(binding.description.context.getString(R.string.notification_passenger_exit, item.user.fullName))
                }
                2 -> {
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


            binding.time.text = time(item.timestamp)

            binding.trip.text = "${item.trip.destinationFrom} - ${item.trip.destinationTo}"

            binding.root.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }
    }


    fun readNotification(notification: Notification) {

        val item = currentList.find { it.id == notification.id }
        if (item != null) {
            val index = currentList.indexOf(item)
            currentList[index].isRead = true
            notifyItemChanged(index)
        }
    }

    private fun time(timestamp: Long): String {
        var test = "πριν από "
        val t = currentTimeStamp() - timestamp
        if (t <= 3600) {
            test += if ((t / 60).toInt() == 1)
                "${(t / 60).toInt()} λεπτό"
            else
                "${(t / 60).toInt()} λεπτά"
        } else if (t <= 3600 * 24)
            test += if ((t / 3600).toInt() == 1)
                "${(t / 3600).toInt()} ώρα"
            else
                "${(t / 3600).toInt()} ώρες"
        else
            test += if ((t / (3600 * 24)).toInt() == 1)
                "${(t / (3600 * 24)).toInt()} μέρα"
            else
                "${(t / (3600 * 24)).toInt()} μέρες"

        return test
    }

    class ViewHolder(val binding: NotificationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


package gr.fellow.fellow_traveller.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.databinding.NotificationItemLayoutBinding
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.ui.extensions.NotificationDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl


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
            binding.trip.text = "${item.trip.destinationFrom} - ${item.trip.destinationTo} "

            binding.root.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }
    }

    class ViewHolder(val binding: NotificationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


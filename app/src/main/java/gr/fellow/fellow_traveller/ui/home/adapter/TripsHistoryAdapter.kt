package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.TripHistoryItemLayoutBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.TripInvolvedDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.setDestination
import gr.fellow.fellow_traveller.utils.convertTimestampToFormat
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp

class TripsHistoryAdapter(
    private val currentUserId: String,
    private val onTripClickListener: (TripInvolved) -> Unit
) : ListAdapter<TripInvolved, TripsHistoryAdapter.ViewHolder>(TripInvolvedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripHistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context

        with(holder) {
            val currentTrip = currentList[position]
            binding.destinationFrom.text = currentTrip.destFrom.title
            binding.destinationTo.setDestination(currentTrip.destTo)
            binding.date.text = getDateFromTimestamp(currentTrip.timestamp, "EEE, dd MMM")

            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }

            if (currentUserId == currentTrip.creatorUser.id) {
                binding.arrow.backgroundTintList = context.resources.getColorStateList(R.color.green_20_new, null)
                binding.arrow.imageTintList = context.resources.getColorStateList(R.color.green, null)
            } else {
                binding.arrow.backgroundTintList = context.resources.getColorStateList(R.color.orange_20_new, null)
                binding.arrow.imageTintList = context.resources.getColorStateList(R.color.orange, null)
            }

            if (position == 0) {
                binding.date.visibility = View.VISIBLE
            } else {
                val previous = currentList[position - 1]
                if (convertTimestampToFormat(currentTrip.timestamp, "dd/MMM/yyyy") != convertTimestampToFormat(previous.timestamp, "dd/MMM/yyyy")) {
                    binding.date.visibility = View.VISIBLE
                } else {
                    binding.date.visibility = View.GONE
                }
            }
        }
    }

    class ViewHolder(val binding: TripHistoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.TripInvolvedHistoryItemLayoutBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.TripInvolvedDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

class TripsHistoryAdapter(
    private val drawable: Int = R.drawable.background_stroke_radius_27_green,
    private val onTripClickListener: (TripInvolved) -> Unit
) : ListAdapter<TripInvolved, TripsHistoryAdapter.ViewHolder>(TripInvolvedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripInvolvedHistoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val currentTrip = currentList[position]
            binding.background.background = ContextCompat.getDrawable(binding.background.context, drawable)
            binding.from.text = currentTrip.destFrom.title
            binding.to.text = currentTrip.destTo.title
            binding.date.text = currentTrip.date
            binding.time.text = currentTrip.time
            binding.name.text = currentTrip.creatorUser.fullName
            binding.picture.loadImageFromUrl(currentTrip.creatorUser.picture)
            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: TripInvolvedHistoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


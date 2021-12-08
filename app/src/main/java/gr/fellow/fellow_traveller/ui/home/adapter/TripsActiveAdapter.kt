package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.TripActiveItemLayoutBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.TripInvolvedDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.setDestination
import gr.fellow.fellow_traveller.ui.extensions.setSpanTextStyle
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp

class TripsActiveAdapter(
    private val currentUserId: String,
    private val onTripClickListener: (TripInvolved) -> Unit
) : ListAdapter<TripInvolved, TripsActiveAdapter.ViewHolder>(TripInvolvedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripActiveItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val context = holder.binding.root.context
        with(holder) {
            val currentTrip = currentList[position]
            binding.destinationFrom.text = currentTrip.destFrom.title
            binding.destinationTo.setDestination(currentTrip.destTo)
            binding.dateTime.text = getDateFromTimestamp(currentTrip.timestamp, "dd/MM HH:mm")
            binding.price.text = context.getString(R.string.active_item_price, currentTrip.price.toString())
            binding.seats.text = context.getString(R.string.active_item_seats, currentTrip.seatAvailable.toString())

            binding.pet.visibility = if (currentTrip.hasPet) View.VISIBLE else View.INVISIBLE
            binding.picture.loadImageFromUrl(currentTrip.creatorUser.picture)

            binding.price.setSpanTextStyle(
                Pair(
                    "${currentTrip.price}${context.getString(R.string.euro_symbol)}/",
                    R.style.paragraph_3_bold_new
                ),
                Pair(
                    context.getString(R.string.per_person),
                    R.style.paragraph_3_new
                )
            )


            if (currentUserId == currentTrip.creatorUser.id) {
                binding.pet.iconTint = context.resources.getColorStateList(R.color.green_90_new, null)
                binding.price.backgroundTintList = context.resources.getColorStateList(R.color.green_60_new, null)
                binding.arrow.backgroundTintList = context.resources.getColorStateList(R.color.green_20_new, null)
                binding.arrow.imageTintList = context.resources.getColorStateList(R.color.green, null)
            } else {
                binding.pet.iconTint = context.resources.getColorStateList(R.color.orange_90_new, null)
                binding.price.backgroundTintList = context.resources.getColorStateList(R.color.orange_60_new, null)
                binding.arrow.backgroundTintList = context.resources.getColorStateList(R.color.orange_20_new, null)
                binding.arrow.imageTintList = context.resources.getColorStateList(R.color.orange, null)
            }


            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: TripActiveItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


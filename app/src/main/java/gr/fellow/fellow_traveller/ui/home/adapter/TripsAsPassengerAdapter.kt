package gr.fellow.fellow_traveller.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.TripInvolvedItemLayoutBinding
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.ui.extensions.TripInvolvedDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

/*
class TripsAsPassengerAdapter(
    private val color: Int = R.color.search_fragment_background,
    private val tripsList: MutableList<TripInvolved>,
    private val onTripClickListener: (TripInvolved) -> Unit
) : RecyclerView.Adapter<TripsAsPassengerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripInvolvedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentTrip = tripsList[position]

        with(holder) {
            binding.background.backgroundTintList = binding.background.context.resources.getColorStateList(color)
            binding.from.text = currentTrip.destFrom.title
            binding.to.text = currentTrip.destTo.title
            binding.name.text = currentTrip.creatorUser.fullName
            binding.date.text = currentTrip.date
            binding.time.text = currentTrip.time
            binding.price.text = binding.price.context.getString(R.string.price, currentTrip.price.toString())
            binding.picture.loadImageFromUrl(currentTrip.creatorUser.picture)
            binding.rate.text = currentTrip.creatorUser.rate.toString()
            binding.review.text = currentTrip.creatorUser.reviews.toString()
            binding.seats.text = currentTrip.seatsStatus
            binding.bags.text = currentTrip.bags
            binding.pet.text = if (currentTrip.hasPet) binding.pet.resources.getString(R.string.allowed) else binding.pet.resources.getString(R.string.not_allowed)
            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: TripInvolvedItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = tripsList.size


}

*/
class TripsAsPassengerAdapter(
    private val color: Int = R.color.search_fragment_background,
    private val onTripClickListener: (TripInvolved) -> Unit
) : ListAdapter<TripInvolved, TripsAsPassengerAdapter.ViewHolder>(TripInvolvedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripInvolvedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val currentTrip = currentList[position]
            binding.background.backgroundTintList = binding.background.context.resources.getColorStateList(color)
            binding.from.text = currentTrip.destFrom.title
            binding.to.text = currentTrip.destTo.title
            binding.name.text = currentTrip.creatorUser.fullName
            binding.date.text = currentTrip.date
            binding.time.text = currentTrip.time
            binding.price.text = binding.price.context.getString(R.string.price, currentTrip.price.toString())
            binding.picture.loadImageFromUrl(currentTrip.creatorUser.picture)
            binding.rate.text = currentTrip.creatorUser.rate.toString()
            binding.review.text = currentTrip.creatorUser.reviews.toString()
            binding.seats.text = currentTrip.seatsStatus
            binding.bags.text = currentTrip.bags
            binding.pet.text = if (currentTrip.hasPet) binding.pet.resources.getString(R.string.allowed) else binding.pet.resources.getString(R.string.not_allowed)
            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: TripInvolvedItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}


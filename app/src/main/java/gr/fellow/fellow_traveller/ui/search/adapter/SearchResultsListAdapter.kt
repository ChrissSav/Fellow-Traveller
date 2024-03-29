package gr.fellow.fellow_traveller.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.SearchResultItemBinding
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.ui.extensions.TripSearchDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

class SearchResultsListAdapter(
    private val onTripClickListener: (TripSearch) -> Unit
) : ListAdapter<TripSearch, SearchResultsListAdapter.ViewHolder>(TripSearchDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentTrip = currentList[position]

        with(holder) {
            binding.from.text = currentTrip.destFrom.title
            binding.to.text = currentTrip.destTo.title
            binding.name.text = currentTrip.creatorUser.fullName
            binding.date.text = currentTrip.date
            binding.time.text = currentTrip.time
            binding.price.text = binding.price.context.getString(R.string.price, currentTrip.price.toString())
            binding.picture.loadImageFromUrl(currentTrip.creatorUser.picture)
            // binding.rate.text = currentTrip.creatorUser.rate.toString()
            // binding.review.text = currentTrip.creatorUser.reviews.toString()
            binding.seats.text = currentTrip.seatsStatus
            binding.bags.text = binding.bags.context.getString(currentTrip.bags.textInt)
            binding.pet.text = if (currentTrip.hasPet) binding.pet.resources.getString(R.string.allowed) else binding.pet.resources.getString(R.string.not_allowed)
            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root)


}
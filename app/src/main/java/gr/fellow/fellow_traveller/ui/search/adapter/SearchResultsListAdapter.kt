package gr.fellow.fellow_traveller.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.SearchResultItemBinding
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.ui.extensions.TripSearchDiffCallback
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl
import gr.fellow.fellow_traveller.ui.extensions.setSpanTextStyle
import gr.fellow.fellow_traveller.utils.getDateFromTimestamp

class SearchResultsListAdapter(
    private val onTripClickListener: (TripSearch) -> Unit
) : ListAdapter<TripSearch, SearchResultsListAdapter.ViewHolder>(TripSearchDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTrip = currentList[position]
        val context = holder.binding.root.context

        with(holder) {
            binding.destinationFromAdministrative.text = currentTrip.destFrom.administrative.title
            binding.destinationFromDes.text = currentTrip.destFrom.title
            binding.destinationToAdministrative.text = currentTrip.destTo.administrative.title
            binding.destinationToDes.text = currentTrip.destTo.title
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

            binding.root.setOnClickListener {
                onTripClickListener.invoke(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root)


}
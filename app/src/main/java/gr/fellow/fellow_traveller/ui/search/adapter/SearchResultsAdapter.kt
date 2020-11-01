package gr.fellow.fellow_traveller.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.databinding.SearchResultItemBinding
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.ui.extensions.loadImageFromUrl

class SearchResultsAdapter(
    private val tripsList: MutableList<TripSearch>,
    private val listener: (TripSearch) -> Unit
) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentTrip = tripsList[position]

        with(holder.binding) {

            from.text = currentTrip.destFrom.title
            to.text = currentTrip.destTo.title
            name.text = currentTrip.creatorUser.fullName
            date.text = currentTrip.date
            time.text = currentTrip.time
            price.text = price.context.getString(R.string.price, currentTrip.price.toString())
            picture.loadImageFromUrl(currentTrip.creatorUser.picture)
            rate.text = currentTrip.creatorUser.rate.toString()
            review.text = currentTrip.creatorUser.reviews.toString()

            this.root.setOnClickListener {
                listener(currentTrip)
            }
        }
    }

    class ViewHolder(val binding: SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = tripsList.size

}
